package com.starry.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.constant.RedisKey;
import com.starry.controller.request.TrafficPageRequest;
import com.starry.controller.request.UseTrafficRequest;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.EventMessageType;
import com.starry.exception.BizException;
import com.starry.feign.ProductFeignService;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.TrafficManager;
import com.starry.mapper.TrafficMapper;
import com.starry.model.EventMessage;
import com.starry.model.LoginUser;
import com.starry.model.TrafficDO;
import com.starry.service.TrafficService;
import com.starry.utils.JsonData;
import com.starry.utils.JsonUtil;
import com.starry.utils.TimeUtil;
import com.starry.vo.ProductVO;
import com.starry.vo.TrafficVO;
import com.starry.vo.UseTrafficVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, TrafficDO>
        implements TrafficService {

    private final TrafficManager trafficManager;
    private final ProductFeignService productFeignService;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void handleTrafficMessage(EventMessage eventMessage) {
        String messageType = eventMessage.getEventMessageType();
        Long accountNo = eventMessage.getAccountNo();
        if (EventMessageType.PRODUCT_ORDER_PAY.name().equalsIgnoreCase(messageType)) {
            //订单已经支付，新增流量
            String content = eventMessage.getContent();
            Map<String, Object> orderInfoMap = JsonUtil.json2Obj(content, Map.class);
            //还原订单商品信息
            String outTradeNo = (String) orderInfoMap.get("outTradeNo");
            Integer buyNum = (Integer) orderInfoMap.get("buyNum");
            String productStr = (String) orderInfoMap.get("product");
            ProductVO productVO = JsonUtil.json2Obj(productStr, ProductVO.class);
            log.info("商品信息:{}", productVO);

            //流量包有效期
            LocalDateTime expiredDateTime = LocalDateTime.now().plusDays(productVO.getValidDay());
            Date date = Date.from(expiredDateTime.atZone(ZoneId.systemDefault()).toInstant());

            //构建流量包对象
            TrafficDO trafficDO = TrafficDO.builder()
                    .accountNo(accountNo)
                    .dayLimit(productVO.getDayTimes() * buyNum)
                    .dayUsed(0)
                    .totalLimit(productVO.getTotalTimes())
                    .pluginType(productVO.getPluginType())
                    .level(productVO.getLevel())
                    .productId(productVO.getId())
                    .outTradeNo(outTradeNo)
                    .expiredDate(date)
                    .build();

            int rows = trafficManager.add(trafficDO);
            log.info("消费消息新增流量包:rows={},trafficDO={}", rows, trafficDO);

            //新增流量包，应该删除这个key
            String totalTrafficTimesKey = String.format(RedisKey.DAY_TOTAL_TRAFFIC, accountNo);
            redisTemplate.delete(totalTrafficTimesKey);
        } else if (EventMessageType.TRAFFIC_FREE_INIT.name().equalsIgnoreCase(messageType)) {
            //发放免费流量包
            long productId = Long.parseLong(eventMessage.getBizId());

            JsonData jsonData = productFeignService.detail(productId);

            ProductVO productVO = jsonData.getData(new TypeReference<ProductVO>() {
            });
            //构建流量包对象
            TrafficDO trafficDO = TrafficDO.builder()
                    .accountNo(accountNo)
                    .dayLimit(productVO.getDayTimes())
                    .dayUsed(0)
                    .totalLimit(productVO.getTotalTimes())
                    .pluginType(productVO.getPluginType())
                    .level(productVO.getLevel())
                    .productId(productVO.getId())
                    .outTradeNo("free_init")
                    .expiredDate(new Date(-1))
                    .build();

            trafficManager.add(trafficDO);
        }
    }

    @Override
    public Map<String, Object> pageAvailable(TrafficPageRequest request) {
        int size = request.getSize();
        int page = request.getPage();
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        IPage<TrafficDO> trafficDOIPage = trafficManager.pageAvailable(page, size, loginUser.getAccountNo());
        //获取流量包列表
        List<TrafficDO> records = trafficDOIPage.getRecords();
        List<TrafficVO> trafficVOList = records.stream().map(this::beanProcess).collect(Collectors.toList());
        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", trafficDOIPage.getTotal());
        pageMap.put("total_page", trafficDOIPage.getPages());
        pageMap.put("current_data", trafficVOList);
        return pageMap;
    }


    @Override
    public TrafficVO detail(long trafficId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        TrafficDO trafficDO = trafficManager.findByIdAndAccountNo(trafficId, loginUser.getAccountNo());
        return beanProcess(trafficDO);
    }

    @Override
    public boolean deleteExpireTraffic() {
        return trafficManager.deleteExpireTraffic();
    }

    /**
     * * 查询用户全部可用流量包
     * * 遍历用户可用流量包
     * * 判断是否更新-用日期判断
     * * 没更新的流量包后加入【待更新集合】中
     * * 增加【今天剩余可用总次数】
     * * 已经更新的判断是否超过当天使用次数
     * * 如果没超过则增加【今天剩余可用总次数】
     * * 超过则忽略
     * <p>
     * * 更新用户今日流量包相关数据
     * * 扣减使用的某个流量包使用次数
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public JsonData reduce(UseTrafficRequest trafficRequest) {
        Long accountNo = trafficRequest.getAccountNo();
        //处理流量包，筛选出未更新流量包，当前使用的流量包
        UseTrafficVO useTrafficVO = processTrafficList(accountNo);
        log.info("今天可用总次数:{},当前使用流量包:{}", useTrafficVO.getDayTotalLeftTimes(), useTrafficVO.getCurrentTrafficDO());
        if (useTrafficVO.getCurrentTrafficDO() == null) {
            return JsonData.buildResult(BizCodeEnum.TRAFFIC_REDUCE_FAIL);
        }
        log.info("待更新流量包列表:{}", useTrafficVO.getUnUpdatedTrafficIds());
        if (useTrafficVO.getUnUpdatedTrafficIds().size() > 0) {
            //更新今日流量包,延迟更新(使用时才进行更新)
            trafficManager.batchUpdateUsedTimes(accountNo, useTrafficVO.getUnUpdatedTrafficIds());
        }
        //扣减当前使用的流量包
        int rows = trafficManager.addDayUsedTimes(accountNo, useTrafficVO.getCurrentTrafficDO().getId(), 1);
        if (rows != 1) {
            throw new BizException(BizCodeEnum.TRAFFIC_REDUCE_FAIL);
        }

        //往redis设置下总流量包次数，短链服务那边递减即可； 如果有新增流量包，则删除这个key
        long leftSeconds = TimeUtil.getRemainSecondsOneDay(new Date());
        String totalTrafficTimesKey = String.format(RedisKey.DAY_TOTAL_TRAFFIC, accountNo);
        // 方法走完流量包就扣减了，所以redis设置的流量包就是-1
        redisTemplate.opsForValue().set(totalTrafficTimesKey,
                useTrafficVO.getDayTotalLeftTimes() - 1, leftSeconds, TimeUnit.SECONDS);
        return JsonData.buildSuccess();
    }

    private UseTrafficVO processTrafficList(Long accountNo) {
        //全部流量包
        List<TrafficDO> list = trafficManager.selectAvailableTraffics(accountNo);
        if (list == null || list.size() == 0) {
            throw new BizException(BizCodeEnum.TRAFFIC_EXCEPTION);
        }
        //天剩余可用总次数
        int dayTotalLeftTimes = 0;
        //当前使用
        TrafficDO currentTrafficDO = null;
        //没过期，但是今天没更新的流量包id列表
        List<Long> unUpdatedTrafficIds = new ArrayList<>();
        //今天日期
        String todayStr = TimeUtil.format(new Date(), "yyyy-MM-dd");

        for (TrafficDO trafficDO : list) {
            String trafficUpdateDate = TimeUtil.format(trafficDO.getGmtModified(), "yyyy-MM-dd");
            if (todayStr.equalsIgnoreCase(trafficUpdateDate)) {
                //已经更新  天剩余可用总次数 = 总次数 - 已用
                int dayLeftTimes = trafficDO.getDayLimit() - trafficDO.getDayUsed();
                dayTotalLeftTimes += dayLeftTimes;
                //选取当次使用流量包
                if (dayLeftTimes > 0 && currentTrafficDO == null) {
                    currentTrafficDO = trafficDO;
                }
            } else {
                //未更新
                dayTotalLeftTimes += trafficDO.getDayLimit();
                //记录未更新的流量包
                unUpdatedTrafficIds.add(trafficDO.getId());
                //选取当次使用流量包
                if (currentTrafficDO == null) {
                    currentTrafficDO = trafficDO;
                }
            }
        }
        return new UseTrafficVO(dayTotalLeftTimes, currentTrafficDO, unUpdatedTrafficIds);
    }


    private TrafficVO beanProcess(TrafficDO trafficDO) {
        TrafficVO trafficVO = new TrafficVO();
        BeanUtils.copyProperties(trafficDO, trafficVO);
        return trafficVO;
    }
}




