package com.hitachi.taskService.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.taskAssignment}")
    private String queueName;

    @Value("${rabbitmq.exchange.taskAssignment}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.taskAssignment}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName,true);  // durable = true
    }
    @Bean
    public Exchange exchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
    }


}
