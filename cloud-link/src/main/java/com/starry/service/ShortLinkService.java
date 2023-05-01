package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.controller.request.ShortLinkPageRequest;
import com.starry.model.EventMessage;
import com.starry.model.ShortLinkDO;
import com.starry.utils.JsonData;
import com.starry.vo.ShortLinkVO;

import java.util.Map;

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

    /**
     * 处理新增短链消息
     * @param eventMessage
     * @return
     */
    boolean handlerAddShortLink(EventMessage eventMessage);

    /**
     * 分页查找短链
     * @param request
     * @return
     */
    Map<String,Object> pageByGroupId(ShortLinkPageRequest request);
}
