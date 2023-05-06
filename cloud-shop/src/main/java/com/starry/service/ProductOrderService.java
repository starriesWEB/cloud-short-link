package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.ConfirmOrderRequest;
import com.starry.controller.request.ProductOrderPageRequest;
import com.starry.enums.ProductOrderPayTypeEnum;
import com.starry.model.EventMessage;
import com.starry.model.ProductOrderDO;
import com.starry.utils.JsonData;

import java.util.Map;

/**
 *
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    Map<String,Object> page(ProductOrderPageRequest orderPageRequest);

    String queryProductOrderState(String outTradeNo);

    JsonData confirmOrder(ConfirmOrderRequest orderRequest);

    boolean closeProductOrder(EventMessage eventMessage);

    /**
     * 处理微信回调通知
     * @param wechatPay
     * @param paramsMap
     */
    JsonData processOrderCallbackMsg(ProductOrderPayTypeEnum wechatPay, Map<String, String> paramsMap);

    /**
     * 处理 队列里面的订单相关消息
     * @param message
     */
    void handleProductOrderMessage(EventMessage message);

}
