package com.starry.strategy;

import com.starry.enums.BizCodeEnum;
import com.starry.exception.BizException;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

public class CustomDBPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //获取短链码第一位，即库位
        String codePrefix = shardingValue.getValue().substring(0, 1);
        for (String targetName : availableTargetNames) {
            //数据源名称最后一位，ds
            String targetNameSuffix = targetName.substring(targetName.length() - 1);
            //返回对应数据源名称，ds0
            if (codePrefix.equals(targetNameSuffix)) {
                return targetName;
            }
        }

        //抛异常
        throw new BizException(BizCodeEnum.DB_ROUTE_NOT_FOUND);

    }
}