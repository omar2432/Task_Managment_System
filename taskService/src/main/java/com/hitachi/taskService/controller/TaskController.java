package com.hitachi.taskService.controller;

import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import com.hitachi.taskService.common.MessageConstants;
import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(MessageConstants.API_BASE_PATH)
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping(MessageConstants.CREATE_TASK_PATH)
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping(MessageConstants.GET_TASK_BY_ID_PATH)
    public ResponseEntity<Task> getTask(@PathVariable long id){
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PutMapping(MessageConstants.UPDATE_TASK_PATH)
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task){
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }


    @DeleteMapping(MessageConstants.DELETE_TASK_PATH)
    public ResponseEntity<Void> deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(MessageConstants.ASSIGN_TASK_PATH)
    public ResponseEntity<Task> assignTaskToUser(@RequestBody TaskAssignmentRequest taskAssignmentRequest){
       return ResponseEntity.ok(taskService.assignTask(taskAssignmentRequest));
    }




}
