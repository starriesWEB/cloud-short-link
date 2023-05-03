package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.constant.TimeConstant;
import com.starry.controller.request.ConfirmOrderRequest;
import com.starry.enums.BillTypeEnum;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.ProductOrderPayTypeEnum;
import com.starry.enums.ProductOrderStateEnum;
import com.starry.exception.BizException;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.ProductManager;
import com.starry.manager.ProductOrderManager;
import com.starry.mapper.ProductOrderMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

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

    @Override
    public Map<String, Object> page(int page, int size, String state) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        return productOrderManager.page(page, size, accountNo, state);
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
     * *  重防􏰀提交（TODO）
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
        ProductOrderDO productOrderDO = this.saveProductOrder(orderRequest,loginUser,orderOutTradeNo,productDO);
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
        //发送延迟消息  TODO
        //调用支付信息 TODO
        return null;
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




