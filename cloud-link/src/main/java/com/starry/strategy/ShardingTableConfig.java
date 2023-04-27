package com.starry.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ShardingTableConfig {

    /**
     * 存储数据表位置编号
     */
    private static final List<String> tableSuffixList = new ArrayList<>();


    //配置启用那些表的后缀
    static {
        tableSuffixList.add("0");
        tableSuffixList.add("a");
    }


    /**
     * 获取随机的后缀
     * @return
     */
    public static String getRandomTableSuffix(){
        return tableSuffixList.get(ThreadLocalRandom.current().nextInt(tableSuffixList.size()));
    }



}