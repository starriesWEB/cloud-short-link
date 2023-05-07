package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starry.manager.TrafficManager;
import com.starry.mapper.TrafficMapper;
import com.starry.model.TrafficDO;
import com.starry.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TrafficManagerImpl implements TrafficManager {


    private final TrafficMapper trafficMapper;

    @Override
    public int add(TrafficDO trafficDO) {
        return trafficMapper.insert(trafficDO);
    }


    @Override
    public IPage<TrafficDO> pageAvailable(int page, int size, Long accountNo) {
        Page<TrafficDO> pageInfo = new Page<>(page, size);
        String today = TimeUtil.format(new Date(), "yyyy-MM-dd");

        Page<TrafficDO> trafficDOPage = trafficMapper.selectPage(pageInfo,
                Wrappers.lambdaQuery(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .eq(TrafficDO::getExpiredDate, today)
                        .orderByDesc(TrafficDO::getGmtCreate)
        );
        return trafficDOPage;
    }

    @Override
    public TrafficDO findByIdAndAccountNo(Long trafficId, Long accountNo) {
        TrafficDO trafficDO = trafficMapper.selectOne(
                Wrappers.lambdaQuery(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .eq(TrafficDO::getId, trafficId)
        );
        return trafficDO;
    }

    /**
     * 给某个流量包增加天使用次数
     *
     * @param currentTrafficId
     * @param accountNo
     * @param dayUsedTimes
     * @return
     */
    @Override
    public int addDayUsedTimes(long currentTrafficId, Long accountNo, int dayUsedTimes) {
        return trafficMapper.update(null,
                Wrappers.lambdaUpdate(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .eq(TrafficDO::getId, currentTrafficId)
                        .set(TrafficDO::getDayUsed, dayUsedTimes)
        );
    }
}