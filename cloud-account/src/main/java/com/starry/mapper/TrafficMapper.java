package com.starry.mapper;

import com.starry.model.TrafficDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.starry.model.TrafficDO
 */
public interface TrafficMapper extends BaseMapper<TrafficDO> {

    int addDayUsedTimes(@Param("accountNo") Long accountNo, @Param("trafficId") Long trafficId, @Param("usedTimes") Integer usedTimes);

}




