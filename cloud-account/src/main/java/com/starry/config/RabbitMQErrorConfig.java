package com.starry.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQErrorConfig {


    /**
     * 异常交换机
     */
    private String trafficErrorExchange = "traffic.error.exchange";

    /**
     * 异常队列
     */
    private String trafficErrorQueue = "traffic.error.queue";

    /**
     * 异常routing.key
     */
    private String trafficErrorRoutingKey = "traffic.error.routing.key";


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建异常交换机
     * @return
     */
    @Bean
    public TopicExchange errorTopicExchange(){

        return new TopicExchange(trafficErrorExchange,true,false);
    }

    /**
     * 创建异常队列
     * @return
     */

    @Bean
    public Queue errorQueue(){
        return new Queue(trafficErrorQueue,true);
    }


    /**
     * 建立绑定关系
     * @return
     */
    @Bean
    public Binding bindingErrorQueueAndExchange(){

        return BindingBuilder.bind(errorQueue()).to(errorTopicExchange()).with(trafficErrorRoutingKey);
    }


    /**
     * 配置  RepublishMessageRecoverer
     *
     * 消费消息重试一定次数后，用特定的routingKey转发到指定的交换机中，方便后续排查和告警
     *
     * @return
     */
    @Bean
    public MessageRecoverer messageRecoverer(){

        return new RepublishMessageRecoverer(rabbitTemplate,trafficErrorExchange,trafficErrorRoutingKey);
    }



}