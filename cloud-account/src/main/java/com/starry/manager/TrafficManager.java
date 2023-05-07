package com.starry.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.starry.model.TrafficDO;

public interface TrafficManager {

    /**
     * 新增流量包
     * @param trafficDO
     * @return
     */
    int add(TrafficDO trafficDO);


    /**
     * 分页查询可用的流量包
     * @param page
     * @param size
     * @param accountNo
     * @return
     */
    IPage<TrafficDO> pageAvailable(int page, int size, Long accountNo);


    /**
     * 查找详情
     * @param trafficId
     * @param accountNo
     * @return
     */
    TrafficDO findByIdAndAccountNo(Long trafficId,Long accountNo);


    /**
     * 增加某个流量包天使用次数
     * @param currentTrafficId
     * @param accountNo
     * @param dayUsedTimes
     * @return
     */
    int addDayUsedTimes(long currentTrafficId, Long accountNo, int dayUsedTimes);


}