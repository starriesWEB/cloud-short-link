package com.starry.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class CustomTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //获取逻辑表
        String targetName = availableTargetNames.iterator().next();
        //短链码  A23Ad1
        String value = shardingValue.getValue();
        //获取短链码最后一位
        String codeSuffix =  value.substring(value.length()-1);
        //拼接Actual table
        return targetName+"_"+codeSuffix;
    }
}