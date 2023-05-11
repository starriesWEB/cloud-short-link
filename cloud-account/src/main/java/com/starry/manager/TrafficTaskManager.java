package com.starry.manager;

import com.starry.model.TrafficTaskDO;

public interface TrafficTaskManager {

    int add(TrafficTaskDO trafficTaskDO);

    TrafficTaskDO findByIdAndAccountNo(Long id,Long accountNo);

    int deleteByIdAndAccountNo(Long id,Long accountNo);

}