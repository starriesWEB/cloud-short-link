package com.starry.listener;

import com.rabbitmq.client.Channel;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.EventMessageType;
import com.starry.exception.BizException;
import com.starry.model.EventMessage;
import com.starry.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
@RabbitListener(queuesToDeclare = { @Queue("short_link.update.mapping.queue") })
public class ShortLinkUpdateMappingMQListener {


    private final ShortLinkService shortLinkService;


    @RabbitHandler
    public void shortLinkHandler(EventMessage eventMessage, Message message, Channel channel) throws IOException {
        log.info("监听到消息ShortLinkUpdateMappingMQListener message消息内容:{}",message);
        try{
            eventMessage.setEventMessageType(EventMessageType.SHORT_LINK_UPDATE_MAPPING.name());
            shortLinkService.handleUpdateShortLink(eventMessage);
        }catch (Exception e){
            log.error("消费失败:{}|{}",eventMessage, ExceptionUtils.getStackTrace(e));
            throw new BizException(BizCodeEnum.MQ_CONSUME_EXCEPTION);
        }
        log.info("消费成功:{}",eventMessage);

    }



}