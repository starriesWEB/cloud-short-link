package com.starry.listener;

import com.rabbitmq.client.Channel;
import com.starry.enums.BizCodeEnum;
import com.starry.exception.BizException;
import com.starry.model.EventMessage;
import com.starry.service.TrafficService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = {
        @Queue("order.traffic.queue"),
        @Queue("traffic.free_init.queue")
})
@Slf4j
public class TrafficMQListener {

    @Autowired
    private TrafficService trafficService;


    @RabbitHandler
    public void trafficHandler(EventMessage eventMessage, Message message, Channel channel){
        log.info("监听到消息trafficHandler:{}",eventMessage);
        try{
            trafficService.handleTrafficMessage(eventMessage);
        }catch (Exception e){
            log.error("消费者失败:{}|{}",eventMessage, ExceptionUtils.getStackTrace(e));
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("消费成功:{}",eventMessage);

    }

}