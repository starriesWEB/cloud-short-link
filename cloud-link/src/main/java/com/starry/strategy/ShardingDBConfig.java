package com.starry.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShardingDBConfig {

    /**
     * 存储数据库位置编号
     */
    private static final List<String> dbPrefixList = new ArrayList<>();


    //配置启用那些库的前缀
    static {
        dbPrefixList.add("0");
        dbPrefixList.add("1");
        dbPrefixList.add("a");
    }


    /**
     * 获取随机的前缀
     * @return
     */
    public static String getRandomDBPrefix(){
        return dbPrefixList.get(ThreadLocalRandom.current().nextInt(dbPrefixList.size()));
    }

}
