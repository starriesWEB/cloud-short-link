package com.starry.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShardingTableConfig {

    /**
     * 存储数据表位置编号
     */
    private static final List<String> TABLE_SUFFIX_LIST = new ArrayList<>();


    //配置启用那些表的后缀
    static {
        TABLE_SUFFIX_LIST.add("0");
        TABLE_SUFFIX_LIST.add("a");
    }


    /**
     * 获取随机的后缀
     * @return
     */
    public static String getRandomTableSuffix(String code){
        return TABLE_SUFFIX_LIST.get(Math.abs(code.hashCode()) % TABLE_SUFFIX_LIST.size());
    }



}