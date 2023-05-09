package com.starry.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.controller.request.TrafficPageRequest;
import com.starry.enums.EventMessageType;
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
import com.starry.vo.ProductVO;
import com.starry.vo.TrafficVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrafficServiceImpl extends ServiceImpl<TrafficMapper, TrafficDO>
    implements TrafficService{

    private final TrafficManager trafficManager;
    private final ProductFeignService productFeignService;

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void handleTrafficMessage(EventMessage eventMessage) {
        String messageType = eventMessage.getEventMessageType();
        Long accountNo = eventMessage.getAccountNo();
        if(EventMessageType.PRODUCT_ORDER_PAY.name().equalsIgnoreCase(messageType)){
            //订单已经支付，新增流量
            String content = eventMessage.getContent();
            Map<String, Object> orderInfoMap = JsonUtil.json2Obj(content,Map.class);
            //还原订单商品信息
            String outTradeNo = (String)orderInfoMap.get("outTradeNo");
            Integer buyNum = (Integer)orderInfoMap.get("buyNum");
            String productStr = (String) orderInfoMap.get("product");
            ProductVO productVO = JsonUtil.json2Obj(productStr, ProductVO.class);
            log.info("商品信息:{}",productVO);

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
            log.info("消费消息新增流量包:rows={},trafficDO={}",rows,trafficDO);
        }else if(EventMessageType.TRAFFIC_FREE_INIT.name().equalsIgnoreCase(messageType)){
            //发放免费流量包
            long productId = Long.parseLong(eventMessage.getBizId());

            JsonData jsonData = productFeignService.detail(productId);

            ProductVO productVO = jsonData.getData(new TypeReference<ProductVO>(){});
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
                    .expiredDate(new Date())
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
        pageMap.put("total_page",trafficDOIPage.getPages());
        pageMap.put("current_data",trafficVOList);
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


    private TrafficVO beanProcess(TrafficDO trafficDO) {
        TrafficVO trafficVO = new TrafficVO();
        BeanUtils.copyProperties(trafficDO,trafficVO);
        return trafficVO;
    }
}




