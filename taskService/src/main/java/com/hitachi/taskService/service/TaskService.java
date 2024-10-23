package com.hitachi.taskService.service;

import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public Task createTask(Task task){
        if(task.getStatus().equals(Task.Status.COMPLETED)){
            task.setCompletedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    public Task getTask(long id){
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
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
        Task task=getTask(id);
        taskRepository.delete(task);
    }

    public Task assignTask(TaskAssignmentRequest taskAssignmentRequest){
        Task task=getTask(taskAssignmentRequest.getTaskId());
        //TODO get user from userService



        //assign task
        task.setAssignedUserId(taskAssignmentRequest.getUserId());
        if(task.getStatus().equals(Task.Status.NOTASSIGNED)){
            task.setStatus(Task.Status.ASSIGNED);
        }

        //TODO Notify via RabbitMQ


        return  taskRepository.save(task);
    }

}
