package com.starry.component;

import com.starry.strategy.ShardingDBConfig;
import com.starry.strategy.ShardingTableConfig;
import com.starry.utils.CommonUtil;
import org.springframework.stereotype.Component;

@Component
public class ShortLinkComponent {

    /**
     * 62个字符
     */
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 生成短链码
     * @param param
     * @return
     */
    public String createShortLinkCode(String param){
        long murmurhash = CommonUtil.murmurHash32(param);
        String code = encodeToBase62(murmurhash);
        return ShardingDBConfig.getRandomDBPrefix() + code + ShardingTableConfig.getRandomTableSuffix();
    }

    /**
     * 10进制转62进制
     * @param num
     * @return
     */
    private String encodeToBase62(long num){
        StringBuilder sb = new StringBuilder();
        do{
            int i = (int )(num%62);
            sb.append(CHARS.charAt(i));
            num = num/62;
        }while (num>0);
        return sb.reverse().toString();

    }



}
