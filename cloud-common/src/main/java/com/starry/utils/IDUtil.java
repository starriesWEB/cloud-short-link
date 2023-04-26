package com.starry.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

@Slf4j
public class IDUtil {
    private static final ShardingKeyGenerator SHARDING_KEY_GENERATOR;

    static {
        SHARDING_KEY_GENERATOR = new SnowflakeShardingKeyGenerator();
        String workId = System.getProperty("workId");
        Properties properties = new Properties();
        properties.setProperty("worker.id", workId);
        SHARDING_KEY_GENERATOR.setProperties(properties);
    }

    /**
     * 雪花算法生成器
     *
     * @return
     */
    public static Comparable<?> genSnowFlakeID() {
        return SHARDING_KEY_GENERATOR.generateKey();
    }

}