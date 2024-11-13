package com.hitachi.userService.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.taskAssignment}")
    private String queueName;

    @Bean
    public Queue taskAssignmentQueue() {
        return new Queue(queueName, true);  // durable = true
    }
}
