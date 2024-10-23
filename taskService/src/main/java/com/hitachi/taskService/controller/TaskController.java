package com.hitachi.taskService.controller;

import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.createTask(task);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTask.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id){
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task){
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/assign")
    public ResponseEntity<Task> assignTaskToUser(@RequestBody TaskAssignmentRequest taskAssignmentRequest){
       return ResponseEntity.ok(taskService.assignTask(taskAssignmentRequest));
    }




}
