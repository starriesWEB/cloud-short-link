package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.component.PayFactory;
import com.starry.config.RabbitMQConfig;
import com.starry.constant.TimeConstant;
import com.starry.controller.request.ConfirmOrderRequest;
import com.starry.controller.request.ProductOrderPageRequest;
import com.starry.enums.*;
import com.starry.exception.BizException;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.ProductManager;
import com.starry.manager.ProductOrderManager;
import com.starry.mapper.ProductOrderMapper;
import com.starry.model.EventMessage;
import com.starry.model.LoginUser;
import com.starry.model.ProductDO;
import com.starry.model.ProductOrderDO;
import com.starry.service.ProductOrderService;
import com.starry.utils.CommonUtil;
import com.starry.utils.JsonData;
import com.starry.utils.JsonUtil;
import com.starry.vo.PayInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrderDO>
    implements ProductOrderService{

    private final ProductOrderManager productOrderManager;
    private final ProductManager productManager;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final StringRedisTemplate redisTemplate;
    private final PayFactory payFactory;

    @Override
    public Map<String, Object> page(ProductOrderPageRequest orderPageRequest) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        return productOrderManager.page(orderPageRequest.getPage(), orderPageRequest.getSize(), accountNo, orderPageRequest.getState());
    }

    @Override
    public String queryProductOrderState(String outTradeNo) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        ProductOrderDO productOrderDO = productOrderManager.findByOutTradeNoAndAccountNo(outTradeNo, accountNo);
        if(productOrderDO == null){
            return "";
        }else {
            return productOrderDO.getState();
        }
    }


    /**
     * *  重防提交（TODO）
     * *  获取最新的流量包价格
     * *  订单验价
     *    *  如果有优惠券或者其他抵扣
     *    *  验证前端显示和后台计算价格
     * *   创建订单对象保存数据库
     * *   发送延迟消息-用于自动关单（TODO）
     * *   创建支付信息-对接三方支付（TODO）
     * *   回调更新订单状态（TODO）
     * *   支付成功创建流量包（TODO）
     * @param orderRequest
     * @return
     */
    @Override
    @Transactional
    public JsonData confirmOrder(ConfirmOrderRequest orderRequest) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String orderOutTradeNo = CommonUtil.getStringNumRandom(32);
        ProductDO productDO = productManager.findDetailById(orderRequest.getProductId());
        //验证价格
        this.checkPrice(productDO,orderRequest);
        //创建订单
        this.saveProductOrder(orderRequest,loginUser,orderOutTradeNo,productDO);
        //创建支付对象
        PayInfoVO payInfoVO = PayInfoVO.builder()
                .accountNo(loginUser.getAccountNo())
                .outTradeNo(orderOutTradeNo)
                .clientType(orderRequest.getClientType())
                .payType(orderRequest.getPayType())
                .title(productDO.getTitle())
                .description("")
                .payFee(orderRequest.getPayAmount())
                .orderPayTimeoutMills(TimeConstant.ORDER_PAY_TIMEOUT_MILLS)
                .build();
        //发送延迟消息
        EventMessage eventMessage = EventMessage.builder()
                .eventMessageType(EventMessageType.PRODUCT_ORDER_NEW.name())
                .accountNo(loginUser.getAccountNo())
                .bizId(orderOutTradeNo)
                .build();
        rabbitTemplate.convertAndSend(rabbitMQConfig.getOrderEventExchange(),rabbitMQConfig.getOrderCloseDelayRoutingKey(),eventMessage);

        //调用支付信息
        String codeUrl = payFactory.pay(payInfoVO);
        if (StringUtils.isNotBlank(codeUrl)) {
            Map<String, String> resultMap = new HashMap<>(2);
            resultMap.put("code_url", codeUrl);
            resultMap.put("out_trade_no", payInfoVO.getOutTradeNo());
            return JsonData.buildSuccess(resultMap);
        }

        return JsonData.buildSuccess();
    }


    /**
     * //延迟消息的时间 需要比订单过期 时间长一点，这样就不存在查询的时候，用户还能支付成功
     *
     * //查询订单是否存在，如果已经支付则正常结束
     * //如果订单未支付，主动调用第三方支付平台查询订单状态
     *     //确认未支付，本地取消订单
     *     //如果第三方平台已经支付，主动的把订单状态改成已支付，造成该原因的情况可能是支付通道回调有问题，然后触发支付后的动作，如何触发？RPC还是？
     * @param eventMessage
     */
    @Override
    public boolean closeProductOrder(EventMessage eventMessage) {

        String outTradeNo = eventMessage.getBizId();
        Long accountNo = eventMessage.getAccountNo();

        ProductOrderDO productOrderDO = productOrderManager.findByOutTradeNoAndAccountNo(outTradeNo, accountNo);

        if(productOrderDO == null){
            //订单不存在
            log.warn("订单不存在");
            return true;
        }

        if(productOrderDO.getState().equalsIgnoreCase(ProductOrderStateEnum.PAY.name())){
            //已经支付
            log.info("直接确认消息，订单已经支付:{}",eventMessage);
            return true;
        }

        //未支付，需要向第三方支付平台查询状态
        if(productOrderDO.getState().equalsIgnoreCase(ProductOrderStateEnum.NEW.name())){
            //向第三方查询状态
            PayInfoVO payInfoVO = new PayInfoVO();
            payInfoVO.setPayType(productOrderDO.getPayType());
            payInfoVO.setOutTradeNo(outTradeNo);
            payInfoVO.setAccountNo(accountNo);

            String payResult = payFactory.queryPayStatus(payInfoVO);

            if(StringUtils.isBlank(payResult)){
                //如果为空，则未支付成功，本地取消订单
                productOrderManager.updateOrderPayState(outTradeNo,accountNo,ProductOrderStateEnum.CANCEL.name(),ProductOrderStateEnum.NEW.name());
                log.info("未支付成功，本地取消订单:{}",eventMessage);
            }else {
                //支付成功，主动把订单状态更新成支付
                log.warn("支付成功，但是微信回调通知失败，需要排查问题:{}",eventMessage);
                productOrderManager.updateOrderPayState(outTradeNo,accountNo,ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.NEW.name());
                //触发支付成功后的逻辑
                Map<String, Object> content = new HashMap<>(4);
                content.put("outTradeNo", outTradeNo);
                content.put("buyNum", productOrderDO.getBuyNum());
                content.put("accountNo", accountNo);
                content.put("product", productOrderDO.getProductSnapshot());

                //构建消息
                EventMessage payEventMessage = EventMessage.builder()
                        .bizId(outTradeNo)
                        .accountNo(accountNo)
                        .messageId(outTradeNo)
                        .content(JsonUtil.obj2Json(content))
                        .eventMessageType(EventMessageType.PRODUCT_ORDER_PAY.name())
                        .build();
                //如果key不存在，则设置成功，返回true
                Boolean flag = redisTemplate.opsForValue().setIfAbsent(outTradeNo, "OK", 3, TimeUnit.DAYS);
                if (Boolean.TRUE.equals(flag)) {
                    rabbitTemplate.convertAndSend(rabbitMQConfig.getOrderEventExchange(),
                            rabbitMQConfig.getOrderUpdateTrafficRoutingKey(), payEventMessage);
                    return false;
                }

            }
        }

        return true;
    }


    /**
     * 处理微信回调通知
     *
     * @param paramsMap
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public JsonData processOrderCallbackMsg(ProductOrderPayTypeEnum payType, Map<String, String> paramsMap) {
        //获取商户订单号
        String outTradeNo = paramsMap.get("out_trade_no");
        //交易状态
        String tradeState = paramsMap.get("trade_state");
        Long accountNo = Long.valueOf(paramsMap.get("account_no"));
        ProductOrderDO productOrderDO = productOrderManager.findByOutTradeNoAndAccountNo(outTradeNo, accountNo);

        Map<String, Object> content = new HashMap<>(4);
        content.put("outTradeNo", outTradeNo);
        content.put("buyNum", productOrderDO.getBuyNum());
        content.put("accountNo", accountNo);
        content.put("product", productOrderDO.getProductSnapshot());

        //构建消息
        EventMessage eventMessage = EventMessage.builder()
                .bizId(outTradeNo)
                .accountNo(accountNo)
                .messageId(outTradeNo)
                .content(JsonUtil.obj2Json(content))
                .eventMessageType(EventMessageType.PRODUCT_ORDER_PAY.name())
                .build();

        if (payType.name().equalsIgnoreCase(ProductOrderPayTypeEnum.ALI_PAY.name())) {
            //支付宝支付 TODO
        } else if (payType.name().equalsIgnoreCase(ProductOrderPayTypeEnum.WECHAT_PAY.name())) {
            if ("SUCCESS".equalsIgnoreCase(tradeState)) {
                // 幂等处理，微信多次回调
                Boolean flag = redisTemplate.opsForValue().setIfAbsent(outTradeNo, "OK", 3, TimeUnit.DAYS);
                if (Boolean.TRUE.equals(flag)) {
                    rabbitTemplate.convertAndSend(rabbitMQConfig.getOrderEventExchange(),
                            rabbitMQConfig.getOrderUpdateTrafficRoutingKey(), eventMessage);

                    return JsonData.buildSuccess();
                }
            }
        }
        return JsonData.buildResult(BizCodeEnum.PAY_ORDER_CALLBACK_NOT_SUCCESS);
    }

    /**
     * 处理订单相关消息
     */
    @Override
    public void handleProductOrderMessage(EventMessage eventMessage) {
        String messageType = eventMessage.getEventMessageType();
        try{
            if(EventMessageType.PRODUCT_ORDER_NEW.name().equalsIgnoreCase(messageType)){
                //关闭订单
                this.closeProductOrder(eventMessage);
            } else if(EventMessageType.PRODUCT_ORDER_PAY.name().equalsIgnoreCase(messageType)){
                //订单已经支付，更新订单状态
                String outTradeNo = eventMessage.getBizId();
                Long accountNo = eventMessage.getAccountNo();
                int rows = productOrderManager.updateOrderPayState(outTradeNo,accountNo,
                        ProductOrderStateEnum.PAY.name(),ProductOrderStateEnum.NEW.name());
                log.info("订单更新成功:rows={},eventMessage={}",rows,eventMessage);
            }
        }catch (Exception e){
            log.error("订单消费者消费失败:{}",eventMessage);
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
    }

    private ProductOrderDO saveProductOrder(ConfirmOrderRequest orderRequest, LoginUser loginUser, String orderOutTradeNo, ProductDO productDO) {
        ProductOrderDO productOrderDO = new ProductOrderDO();
        //设置用户信息
        productOrderDO.setAccountNo(loginUser.getAccountNo());
        productOrderDO.setNickname(loginUser.getUsername());
        //设置商品信息
        productOrderDO.setProductId(productDO.getId());
        productOrderDO.setProductTitle(productDO.getTitle());
        productOrderDO.setProductSnapshot(JsonUtil.obj2Json(productDO));
        productOrderDO.setProductAmount(productDO.getAmount());
        //设置订单信息
        productOrderDO.setBuyNum(orderRequest.getBuyNum());
        productOrderDO.setOutTradeNo(orderOutTradeNo);
        productOrderDO.setCreateTime(LocalDateTime.now());
        productOrderDO.setDel(0);
        //发票信息
        productOrderDO.setBillType(BillTypeEnum.valueOf(orderRequest.getBillType()).name());
        productOrderDO.setBillHeader(orderRequest.getBillHeader());
        productOrderDO.setBillReceiverPhone(orderRequest.getBillReceiverPhone());
        productOrderDO.setBillReceiverEmail(orderRequest.getBillReceiverEmail());
        productOrderDO.setBillContent(orderRequest.getBillContent());
        //实际支付总价
        productOrderDO.setPayAmount(orderRequest.getPayAmount());
        //总价，没使用优惠券
        productOrderDO.setTotalAmount(orderRequest.getTotalAmount());
        //订单状态
        productOrderDO.setState(ProductOrderStateEnum.NEW.name());
        //支付类型
        productOrderDO.setPayType(ProductOrderPayTypeEnum.valueOf(orderRequest.getPayType()).name());
        //插入数据库
        productOrderManager.add(productOrderDO);

        return productOrderDO;
    }


    private void checkPrice(ProductDO productDO, ConfirmOrderRequest orderRequest) {
        //后端计算价格
        BigDecimal bizTotal = BigDecimal.valueOf(orderRequest.getBuyNum()).multiply(productDO.getAmount());
        //前端传递总价和后端计算总价格是否一致, 如果有优惠券，也在这里进行计算
        if( bizTotal.compareTo(orderRequest.getPayAmount()) !=0 ){
            log.error("验证价格失败{}",orderRequest);
            throw new BizException(BizCodeEnum.ORDER_CONFIRM_PRICE_FAIL);
        }

    }

}




