package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.starry.manager.TrafficTaskManager;
import com.starry.mapper.TrafficTaskMapper;
import com.starry.model.TrafficTaskDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TrafficTaskManagerImpl implements TrafficTaskManager {

    private final TrafficTaskMapper trafficTaskMapper;

    @Override
    public int add(TrafficTaskDO trafficTaskDO) {
        return trafficTaskMapper.insert(trafficTaskDO);
    }

    @Override
    public TrafficTaskDO findByIdAndAccountNo(Long id, Long accountNo) {
        return trafficTaskMapper.selectOne(
                Wrappers.lambdaQuery(TrafficTaskDO.class)
                        .eq(TrafficTaskDO::getId, id)
                        .eq(TrafficTaskDO::getAccountNo, accountNo)
        );
    }

    @Override
    public int deleteByIdAndAccountNo(Long id, Long accountNo) {
        return trafficTaskMapper.delete(
                Wrappers.lambdaQuery(TrafficTaskDO.class)
                        .eq(TrafficTaskDO::getId, id)
                        .eq(TrafficTaskDO::getAccountNo, accountNo)
        );
    }
}