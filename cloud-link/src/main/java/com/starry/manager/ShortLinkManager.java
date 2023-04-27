package com.starry.manager;

import com.starry.model.ShortLinkDO;

public interface ShortLinkManager {

    /**
     * 新增
     * @param shortLinkDO
     * @return
     */
    int addShortLink(ShortLinkDO shortLinkDO);


    /**
     * 根据短链码找短链
     * @param shortLinkCode
     * @return
     */
    ShortLinkDO findByShortLinCode(String shortLinkCode);


    /**
     * 删除
     * @param shortLinkCode
     * @param accountNo
     * @return
     */
    int del(String shortLinkCode,Long accountNo);

}