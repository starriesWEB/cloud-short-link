package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.model.ShortLinkDO;
import com.starry.utils.JsonData;
import com.starry.vo.ShortLinkVO;

/**
 *
 */
public interface ShortLinkService extends IService<ShortLinkDO> {


    /**
     * 解析短链
     * @param shortLinkCode
     * @return
     */
    ShortLinkVO parseShortLinkCode(String shortLinkCode);

    /**
     * 创建短链
     * @param request
     * @return
     */
    JsonData createShortLink(ShortLinkAddRequest request);
}
