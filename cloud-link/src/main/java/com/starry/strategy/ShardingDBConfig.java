package com.starry.strategy;

import java.util.ArrayList;
import java.util.List;

public class ShardingDBConfig {

    /**
     * 存储数据库位置编号
     */
    private static final List<String> DB_PREFIX_LIST = new ArrayList<>();


    //配置启用那些库的前缀
    static {
        DB_PREFIX_LIST.add("0");
        DB_PREFIX_LIST.add("1");
        DB_PREFIX_LIST.add("a");
    }


    /**
     * 获取随机的前缀
     * @return
     */
    public static String getRandomDBPrefix(String code){
        return DB_PREFIX_LIST.get(Math.abs(code.hashCode()) % DB_PREFIX_LIST.size());
    }

}
