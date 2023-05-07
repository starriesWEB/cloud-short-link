package com.starry.listener;

import com.rabbitmq.client.Channel;
import com.starry.enums.BizCodeEnum;
import com.starry.exception.BizException;
import com.starry.model.EventMessage;
import com.starry.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queuesToDeclare = {
        @Queue("order.close.queue"),
        @Queue("order.update.queue")
})
public class ProductOrderMQListener {



    private final ProductOrderService productOrderService;


    @RabbitHandler
    public void productOrderHandler(EventMessage eventMessage, Message message, Channel channel){
        log.info("监听到消息ProductOrderMQListener messsage消息内容:{}",message);
        try{
            productOrderService.handleProductOrderMessage(eventMessage);
        }catch (Exception e){
            log.error("消费者失败:{}",eventMessage);
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("消费成功:{}",eventMessage);


    }


}
