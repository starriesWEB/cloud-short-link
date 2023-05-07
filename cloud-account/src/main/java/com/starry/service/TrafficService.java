package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.TrafficPageRequest;
import com.starry.model.EventMessage;
import com.starry.model.TrafficDO;
import com.starry.vo.TrafficVO;

import java.util.Map;

/**
 *
 */
public interface TrafficService extends IService<TrafficDO> {


    void handleTrafficMessage(EventMessage eventMessage);

    Map<String,Object> pageAvailable(TrafficPageRequest request);

    TrafficVO detail(long trafficId);
}
