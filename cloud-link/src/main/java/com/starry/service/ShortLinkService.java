package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.controller.request.ShortLinkDelRequest;
import com.starry.controller.request.ShortLinkPageRequest;
import com.starry.controller.request.ShortLinkUpdateRequest;
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
     * 分页查找短链
     * @param request
     * @return
     */
    Map<String,Object> pageByGroupId(ShortLinkPageRequest request);


    /**
     * 删除短链
     *
     * @param request
     * @return
     */
    JsonData del(ShortLinkDelRequest request);

    /**
     * 更新
     *
     * @param request
     * @return
     */
    JsonData update(ShortLinkUpdateRequest request);


    /**
     * 处理新增短链消息
     * @param eventMessage
     * @return
     */
    boolean handlerAddShortLink(EventMessage eventMessage);

    /**
     * 更新短链
     *
     * @param eventMessage
     * @return
     */
    boolean handleUpdateShortLink(EventMessage eventMessage);


    /**
     * 删除短链
     * @param eventMessage
     * @return
     */
    boolean handleDelShortLink(EventMessage eventMessage);
}
