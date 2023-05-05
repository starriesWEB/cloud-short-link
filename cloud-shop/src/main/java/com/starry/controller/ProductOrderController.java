package com.starry.controller;

import com.starry.annotation.RepeatSubmit;
import com.starry.constant.RedisKey;
import com.starry.controller.request.ConfirmOrderRequest;
import com.starry.controller.request.ProductOrderPageRequest;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.ClientTypeEnum;
import com.starry.enums.ProductOrderPayTypeEnum;
import com.starry.interceptor.LoginInterceptor;
import com.starry.service.ProductOrderService;
import com.starry.utils.CommonUtil;
import com.starry.utils.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/order/v1")
@Slf4j
@RequiredArgsConstructor
public class ProductOrderController {


    private final ProductOrderService productOrderService;
    private final StringRedisTemplate redisTemplate;


    /**
     * 下单前获取令牌用于防重提交
     *
     * @return
     */
    @GetMapping("token")
    public JsonData getOrderToken() {
        long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        String token = CommonUtil.getStringNumRandom(32);
        String key = String.format(RedisKey.SUBMIT_ORDER_TOKEN_KEY, accountNo, token);
        //令牌有效时间是30分钟
        redisTemplate.opsForValue().set(key, String.valueOf(Thread.currentThread().getId()), 30, TimeUnit.MINUTES);
        return JsonData.buildSuccess(token);
    }

    /**
     * 分页接口
     *
     * @return
     */
    @PostMapping("page")
    public JsonData page(
            @RequestBody ProductOrderPageRequest orderPageRequest
    ) {
        Map<String, Object> pageResult = productOrderService.page(orderPageRequest);
        return JsonData.buildSuccess(pageResult);
    }


    /**
     * 查询订单状态
     *
     * @param outTradeNo
     * @return
     */
    @GetMapping("query_state")
    public JsonData queryState(@RequestParam(value = "out_trade_no") String outTradeNo) {
        String state = productOrderService.queryProductOrderState(outTradeNo);
        return StringUtils.isBlank(state) ?
                JsonData.buildResult(BizCodeEnum.ORDER_CONFIRM_NOT_EXIST) : JsonData.buildSuccess(state);

    }


    /**
     * 下单接口
     *
     * @param orderRequest
     * @param response
     */
    @PostMapping("confirm")
    @RepeatSubmit(limitType = RepeatSubmit.Type.TOKEN)
    public void confirmOrder(@RequestBody ConfirmOrderRequest orderRequest, HttpServletResponse response) {
        JsonData jsonData = productOrderService.confirmOrder(orderRequest);
        if (jsonData.getCode() == 0) {
            //端类型
            String client = orderRequest.getClientType();
            //支付类型
            String payType = orderRequest.getPayType();
            //如果是支付宝支付，跳转网页，sdk除非
            if (payType.equalsIgnoreCase(ProductOrderPayTypeEnum.ALI_PAY.name())) {
                if (client.equalsIgnoreCase(ClientTypeEnum.PC.name())) {
                    CommonUtil.sendHtmlMessage(response, jsonData);
                } else if (client.equalsIgnoreCase(ClientTypeEnum.APP.name())) {
                } else if (client.equalsIgnoreCase(ClientTypeEnum.H5.name())) {
                }

            } else if (payType.equalsIgnoreCase(ProductOrderPayTypeEnum.WECHAT_PAY.name())) {
                //微信支付
                CommonUtil.sendJsonMessage(response, jsonData);
            }
        } else {
            log.error("创建订单失败{}", jsonData);
            CommonUtil.sendJsonMessage(response, jsonData);
        }
    }


}