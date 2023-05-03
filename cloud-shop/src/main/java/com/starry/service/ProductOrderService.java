package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.ConfirmOrderRequest;
import com.starry.model.ProductOrderDO;
import com.starry.utils.JsonData;

import java.util.Map;

/**
 *
 */
public interface ProductOrderService extends IService<ProductOrderDO> {

    Map<String,Object> page(int page, int size, String state);

    String queryProductOrderState(String outTradeNo);

    JsonData confirmOrder(ConfirmOrderRequest orderRequest);

}
