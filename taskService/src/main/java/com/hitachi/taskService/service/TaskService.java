package com.hitachi.taskService.service;

import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import com.hitachi.taskService.DTO.UserResponse;
import com.hitachi.taskService.common.MessageConstants;
import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.repository.TaskRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;
    private final TaskAssignmentMessagePublisher taskAssignmentMessagePublisher;


    @Autowired
    public TaskService(TaskRepository taskRepository, UserClient userClient,TaskAssignmentMessagePublisher taskAssignmentMessagePublisher){

        this.taskRepository=taskRepository;
        this.userClient=userClient;
        this.taskAssignmentMessagePublisher=taskAssignmentMessagePublisher;
    }
    public Task createTask(Task task){
        if(task.getStatus().equals(Task.Status.COMPLETED)){
            task.setCompletedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    public Task getTask(long id){
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageConstants.TASK_NOT_FOUND+id));
    }

    public Task updateTask(long id,Task newTask){
        Task task=getTask(id);
        task.setDescription(newTask.getDescription());
        // if the task was not completed but is completed now update completedAt
        if((!task.getStatus().equals(Task.Status.COMPLETED)) && newTask.getStatus().equals(Task.Status.COMPLETED)){
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setStatus(newTask.getStatus());

        return taskRepository.save(task);
    }

    public void deleteTask(long id){
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException(MessageConstants.TASK_NOT_FOUND+id);
        }
        taskRepository.deleteById(id);
    }

    public Task assignTask(TaskAssignmentRequest taskAssignmentRequest){
        Task task=getTask(taskAssignmentRequest.getTaskId());

        //get user from userService
        try {
            UserResponse user= userClient.getUserById(taskAssignmentRequest.getUserId()).getBody();
            System.out.println(MessageConstants.USER_FOUND +user.getId());

        } catch (FeignException.NotFound e) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND);
        }

        //assign task
        task.setAssignedUserId(taskAssignmentRequest.getUserId());
        if(task.getStatus().equals(Task.Status.NOTASSIGNED)){
            task.setStatus(Task.Status.ASSIGNED);
        }

        // Notify via RabbitMQ
        taskAssignmentMessagePublisher.publishTaskAssignment(taskAssignmentRequest);


        return  taskRepository.save(task);
    }

}
