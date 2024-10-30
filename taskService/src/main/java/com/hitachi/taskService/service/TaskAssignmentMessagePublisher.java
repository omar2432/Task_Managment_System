package com.hitachi.taskService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentMessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Jackson ObjectMapper

    @Value("${rabbitmq.exchange.taskAssignment}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.taskAssignment}")
    private String routingKey;

    public void publishTaskAssignment(TaskAssignmentRequest taskAssignmentRequest){

        try {
            // Serialize the object to JSON
            String jsonMessage = objectMapper.writeValueAsString(taskAssignmentRequest);
            rabbitTemplate.convertAndSend(
                    exchangeName,
                    routingKey,
                    jsonMessage // Send the JSON string
            );
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
        }

    }


}
