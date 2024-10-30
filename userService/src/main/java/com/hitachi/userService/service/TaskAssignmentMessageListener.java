package com.hitachi.userService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.userService.DTO.TaskAssignmentMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "#{@environment.getProperty('rabbitmq.queue.taskAssignment')}")
    public void handleTaskAssignmentMessage(String message){

        try {
            // Deserialize the JSON string to TaskAssignmentMessage
            TaskAssignmentMessage taskAssignmentMessage=objectMapper.readValue(message, TaskAssignmentMessage.class);
            System.out.println("****** "
                    +"The Task with ID: "
                    +taskAssignmentMessage.getTaskId()
                    +" was assigned to the user with ID: "
                    +taskAssignmentMessage.getUserId()
                    +" ******");
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
        }


    }

}
