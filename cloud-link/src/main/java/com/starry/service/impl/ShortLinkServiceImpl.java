package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.component.ShortLinkComponent;
import com.starry.config.RabbitMQConfig;
import com.starry.constant.RedisKey;
import com.starry.controller.request.*;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.DomainTypeEnum;
import com.starry.enums.EventMessageType;
import com.starry.enums.ShortLinkStateEnum;
import com.starry.feign.TrafficFeignService;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.DomainManager;
import com.starry.manager.GroupCodeMappingManager;
import com.starry.manager.LinkGroupManager;
import com.starry.manager.ShortLinkManager;
import com.starry.mapper.ShortLinkMapper;
import com.starry.model.*;
import com.starry.service.ShortLinkService;
import com.starry.utils.CommonUtil;
import com.starry.utils.IDUtil;
import com.starry.utils.JsonData;
import com.starry.utils.JsonUtil;
import com.starry.vo.ShortLinkVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO>
        implements ShortLinkService {

    private final ShortLinkManager shortLinkManager;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final ShortLinkComponent shortLinkComponent;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final GroupCodeMappingManager groupCodeMappingManager;
    private final DomainManager domainManager;
    private final LinkGroupManager linkGroupManager;
    private final TrafficFeignService trafficFeignService;


    private static final DefaultRedisScript<Long> LOCK_SCRIPT;
    private static final DefaultRedisScript<Long> TRAFFIC_DAY_SCRIPT;

    static {
        // KEYS[1]是短链码，ARGV[1]是accountNo,ARGV[2]是过期时间
        String script1 = "if redis.call('EXISTS',KEYS[1])==0 then redis.call('set',KEYS[1],ARGV[1]); redis.call('expire',KEYS[1],ARGV[2]); return 1;" +
                " elseif redis.call('get',KEYS[1]) == ARGV[1] then return 2;" +
                " else return 0; end;";
        LOCK_SCRIPT = new DefaultRedisScript<>(script1, Long.class);

        // 当天流量包-1
        String script2 = "if redis.call('get',KEYS[1]) then return redis.call('decr',KEYS[1]) else return 0 end";
        TRAFFIC_DAY_SCRIPT = new DefaultRedisScript<>(script2, Long.class);
    }


    @Override
    public ShortLinkVO parseShortLinkCode(String shortLinkCode) {
        ShortLinkDO shortLinkDO = shortLinkManager.findByShortLinCode(shortLinkCode);
        if (shortLinkDO == null) {
            return null;
        }
        ShortLinkVO shortLinkVO = new ShortLinkVO();
        BeanUtils.copyProperties(shortLinkDO, shortLinkVO);
        return shortLinkVO;
    }

    @Override
    public JsonData createShortLink(ShortLinkAddRequest request) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();

        String cacheKey = String.format(RedisKey.DAY_TOTAL_TRAFFIC, accountNo);
        /*
        流量包预扣减，key 是否存在
        key 不存在就是当天未更新，或者购买了新流量包，返回0
        key 存在，-1 后判断是否 >0
         */
        Long leftTimes = redisTemplate.execute(TRAFFIC_DAY_SCRIPT, Collections.singletonList(cacheKey), StringUtils.EMPTY);
        log.info("今日流量包剩余次数:{}", leftTimes);

        if (leftTimes >= 0) {
            String prefixUrl = CommonUtil.addUrlPrefix(request.getOriginalUrl());
            request.setOriginalUrl(prefixUrl);
            EventMessage eventMessage = EventMessage.builder()
                    .accountNo(accountNo)
                    .content(JsonUtil.obj2Json(request))
                    .messageId(IDUtil.genSnowFlakeID().toString())
                    .eventMessageType(EventMessageType.SHORT_LINK_ADD.name())
                    .build();

            rabbitTemplate.convertAndSend(rabbitMQConfig.getShortLinkEventExchange(), rabbitMQConfig.getShortLinkAddRoutingKey(), eventMessage);
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildResult(BizCodeEnum.TRAFFIC_REDUCE_FAIL);
        }

    }

    /**
     * 处理短链新增逻辑
     * <p>
     * //判断短链域名是否合法
     * //判断组名是否合法
     * //生成长链摘要
     * //生成短链码
     * //加锁
     * //查询短链码是否存在
     * //构建短链对象
     * //保存数据库
     *
     * @param eventMessage
     * @return
     */
    @Override
    public boolean handlerAddShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccountNo();
        String messageType = eventMessage.getEventMessageType();
        ShortLinkAddRequest addRequest = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkAddRequest.class);
        //短链域名校验
        DomainDO domainDO = checkDomain(addRequest.getDomainType(), addRequest.getDomainId(), accountNo);
        //校验组是否合法
        LinkGroupDO linkGroupDO = checkLinkGroup(addRequest.getGroupId(), accountNo);
        //长链摘要
        String originalUrlDigest = CommonUtil.MD5(addRequest.getOriginalUrl());
        //生成短链码
        String shortLinkCode = shortLinkComponent.createShortLinkCode(addRequest.getOriginalUrl());
        //加锁
        Long result = redisTemplate.execute(LOCK_SCRIPT, Collections.singletonList(shortLinkCode), accountNo, 100);
        //加锁成功
        //短链码重复标记
        boolean duplicateCodeFlag = false;
        if (result > 0) {
            if (EventMessageType.SHORT_LINK_ADD_LINK.name().equalsIgnoreCase(messageType)) {
                //先判断是否短链码被占用
                ShortLinkDO shortLinCodeDOInDB = shortLinkManager.findByShortLinCode(shortLinkCode);
                if (shortLinCodeDOInDB == null) {
                    boolean reduceFlag = reduceTraffic(eventMessage, shortLinkCode);
                    if (reduceFlag) {
                        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                                .accountNo(accountNo)
                                .code(shortLinkCode)
                                .title(addRequest.getTitle())
                                .originalUrl(addRequest.getOriginalUrl())
                                .domain(domainDO.getValue())
                                .groupId(linkGroupDO.getId())
                                .expired(addRequest.getExpired())
                                .sign(originalUrlDigest)
                                .state(ShortLinkStateEnum.ACTIVE.name())
                                .del(0)
                                .build();
                        shortLinkManager.addShortLink(shortLinkDO);
                        return true;
                    } else {
                        log.error("C端流量包无可用额度{}", eventMessage);
                        return false;
                    }

                } else {
                    log.error("C端短链码重复:{}", eventMessage);
                    duplicateCodeFlag = true;
                }
            } else if (EventMessageType.SHORT_LINK_ADD_MAPPING.name().equalsIgnoreCase(messageType)) {
                GroupCodeMappingDO groupCodeMappingDOInDB = groupCodeMappingManager.findByCodeAndGroupId(shortLinkCode, linkGroupDO.getId(), accountNo);
                if (groupCodeMappingDOInDB == null) {
                    GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                            .accountNo(accountNo)
                            .code(shortLinkCode)
                            .title(addRequest.getTitle())
                            .originalUrl(addRequest.getOriginalUrl())
                            .domain(domainDO.getValue())
                            .groupId(linkGroupDO.getId())
                            .expired(addRequest.getExpired())
                            .sign(originalUrlDigest)
                            .state(ShortLinkStateEnum.ACTIVE.name())
                            .del(0)
                            .build();
                    groupCodeMappingManager.add(groupCodeMappingDO);
                    return true;
                } else {
                    log.error("B端短链码重复:{}", eventMessage);
                    duplicateCodeFlag = true;
                }
            }
        } else {
            //加锁失败，自旋100毫秒，再调用； 失败的可能是短链码已经被占用，需要重新生成
            log.error("加锁失败:{}", eventMessage);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ignored) {
            }
            duplicateCodeFlag = true;
        }

        if (duplicateCodeFlag) {
            String newOriginalUrl = CommonUtil.addUrlPrefixVersion(addRequest.getOriginalUrl());
            addRequest.setOriginalUrl(newOriginalUrl);
            eventMessage.setContent(JsonUtil.obj2Json(addRequest));
            log.warn("短链码生成失败，重新生成:{}", eventMessage);
            handlerAddShortLink(eventMessage);
        }
        return false;
    }

    /**
     * 扣减流量包
     *
     * @param eventMessage
     * @param shortLinkCode
     * @return
     */
    private boolean reduceTraffic(EventMessage eventMessage, String shortLinkCode) {
        UseTrafficRequest request = UseTrafficRequest.builder()
                .accountNo(eventMessage.getAccountNo())
                .bizId(shortLinkCode)
                .build();

        JsonData jsonData = trafficFeignService.useTraffic(request);
        if (jsonData.getCode() != 0) {
            log.error("流量包不足，扣减失败:{}", eventMessage);
            return false;
        }
        return true;
    }

    @Override
    public boolean handleUpdateShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccountNo();
        String messageType = eventMessage.getEventMessageType();
        ShortLinkUpdateRequest request = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkUpdateRequest.class);
        DomainDO domainDO = checkDomain(request.getDomainType(), request.getDomainId(), accountNo);
        if (EventMessageType.SHORT_LINK_UPDATE_LINK.name().equalsIgnoreCase(messageType)) {
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .code(request.getCode())
                    .title(request.getTitle())
                    .domain(domainDO.getValue())
                    .accountNo(accountNo)
                    .build();
            int rows = shortLinkManager.update(shortLinkDO);
            log.debug("更新C端短链，rows={}", rows);
            return true;
        } else if (EventMessageType.SHORT_LINK_UPDATE_MAPPING.name().equalsIgnoreCase(messageType)) {
            GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                    .id(request.getMappingId())
                    .groupId(request.getGroupId())
                    .accountNo(accountNo)
                    .title(request.getTitle())
                    .domain(domainDO.getValue())
                    .build();
            int rows = groupCodeMappingManager.update(groupCodeMappingDO);
            log.debug("更新B端短链，rows={}", rows);
            return true;
        }
        return false;
    }


    @Override
    public boolean handleDelShortLink(EventMessage eventMessage) {
        Long accountNo = eventMessage.getAccountNo();
        String messageType = eventMessage.getEventMessageType();
        ShortLinkDelRequest request = JsonUtil.json2Obj(eventMessage.getContent(), ShortLinkDelRequest.class);
        if (EventMessageType.SHORT_LINK_DEL_LINK.name().equalsIgnoreCase(messageType)) {
            ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                    .code(request.getCode())
                    .accountNo(accountNo)
                    .build();
            int rows = shortLinkManager.del(shortLinkDO);
            log.debug("删除C端短链:{}", rows);
            return true;
        } else if (EventMessageType.SHORT_LINK_DEL_MAPPING.name().equalsIgnoreCase(messageType)) {
            GroupCodeMappingDO groupCodeMappingDO = GroupCodeMappingDO.builder()
                    .id(request.getMappingId())
                    .accountNo(accountNo)
                    .groupId(request.getGroupId())
                    .code(request.getCode())
                    .build();
            int rows = groupCodeMappingManager.del(groupCodeMappingDO);
            log.debug("删除B端短链:{}", rows);
            return true;
        }
        return false;
    }

    /**
     * 从B端查找，group_code_mapping表
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> pageByGroupId(ShortLinkPageRequest request) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        return groupCodeMappingManager.pageShortLinkByGroupId(request.getPage(), request.getSize(), accountNo, request.getGroupId());
    }

    @Override
    public JsonData del(ShortLinkDelRequest request) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        EventMessage eventMessage = EventMessage.builder().accountNo(accountNo)
                .content(JsonUtil.obj2Json(request))
                .messageId(IDUtil.genSnowFlakeID().toString())
                .eventMessageType(EventMessageType.SHORT_LINK_DEL.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMQConfig.getShortLinkEventExchange(), rabbitMQConfig.getShortLinkDelRoutingKey(), eventMessage);
        return JsonData.buildSuccess();

    }


    @Override
    public JsonData update(ShortLinkUpdateRequest request) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        EventMessage eventMessage = EventMessage.builder().accountNo(accountNo)
                .content(JsonUtil.obj2Json(request))
                .messageId(IDUtil.genSnowFlakeID().toString())
                .eventMessageType(EventMessageType.SHORT_LINK_UPDATE.name())
                .build();
        rabbitTemplate.convertAndSend(rabbitMQConfig.getShortLinkEventExchange(), rabbitMQConfig.getShortLinkUpdateRoutingKey(), eventMessage);
        return JsonData.buildSuccess();
    }


    /**
     * 校验域名
     *
     * @param domainType
     * @param domainId
     * @param accountNo
     * @return
     */
    private DomainDO checkDomain(String domainType, Long domainId, Long accountNo) {
        DomainDO domainDO;
        if (DomainTypeEnum.CUSTOM.name().equalsIgnoreCase(domainType)) {
            domainDO = domainManager.findById(domainId, accountNo);
        } else {
            domainDO = domainManager.findByDomainTypeAndID(domainId, DomainTypeEnum.OFFICIAL);
        }
        Assert.notNull(domainDO, "短链域名不合法");
        return domainDO;
    }

    /**
     * 校验组名
     *
     * @param groupId
     * @param accountNo
     * @return
     */
    private LinkGroupDO checkLinkGroup(Long groupId, Long accountNo) {
        LinkGroupDO linkGroupDO = linkGroupManager.detail(groupId, accountNo);
        Assert.notNull(linkGroupDO, "组名不合法");
        return linkGroupDO;
    }

}




