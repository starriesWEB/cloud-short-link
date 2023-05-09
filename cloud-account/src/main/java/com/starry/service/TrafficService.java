package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.TrafficPageRequest;
import com.starry.controller.request.UseTrafficRequest;
import com.starry.model.EventMessage;
import com.starry.model.TrafficDO;
import com.starry.utils.JsonData;
import com.starry.vo.TrafficVO;

import java.util.Map;

/**
 *
 */
public interface TrafficService extends IService<TrafficDO> {


    void handleTrafficMessage(EventMessage eventMessage);

    Map<String,Object> pageAvailable(TrafficPageRequest request);

    TrafficVO detail(long trafficId);

    /**
     * 删除过期流量包
     * @return
     */
    boolean deleteExpireTraffic();

    /**
     * 扣减流量包
     * @param useTrafficRequest
     * @return
     */
    JsonData reduce(UseTrafficRequest useTrafficRequest);
}
