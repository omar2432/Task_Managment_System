package com.hitachi.taskService.controller;

import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.exchange.request.TaskAssignmentRequest;
import com.hitachi.taskService.common.MessageConstants;
import com.hitachi.taskService.exchange.request.TaskReqDTO;
import com.hitachi.taskService.exchange.response.TaskResDTO;
import com.hitachi.taskService.mapper.TaskMapper;
import com.hitachi.taskService.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MessageConstants.API_BASE_PATH)
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper){
        this.taskService=taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(MessageConstants.CREATE_TASK_PATH)
    public ResponseEntity<TaskResDTO> createTask(@RequestBody @Valid TaskReqDTO taskReqDTO){
        Task createdTask=taskService.createTask(taskMapper.mapToEntity(taskReqDTO));
        TaskResDTO taskResDTO =taskMapper.mapToDTO(createdTask) ;
        return new ResponseEntity<TaskResDTO>(taskResDTO, HttpStatus.CREATED);
    }

    @GetMapping(MessageConstants.GET_TASK_BY_ID_PATH)
    public ResponseEntity<TaskResDTO> getTask(@PathVariable long id){
        TaskResDTO taskResDTO=taskMapper.mapToDTO(taskService.getTask(id));
        return ResponseEntity.ok(taskResDTO);
    }

    @PutMapping(MessageConstants.UPDATE_TASK_PATH)
    public ResponseEntity<TaskResDTO> updateTask(@PathVariable long id, @RequestBody @Valid TaskReqDTO taskReqDTO){
        Task task=taskMapper.mapToEntity(taskReqDTO);
        TaskResDTO taskResDTO=taskMapper.mapToDTO(taskService.updateTask(id,task));
        return ResponseEntity.ok(taskResDTO);
    }


    @DeleteMapping(MessageConstants.DELETE_TASK_PATH)
    public ResponseEntity<Void> deleteTask(@PathVariable long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping(MessageConstants.ASSIGN_TASK_PATH)
    public ResponseEntity<TaskResDTO> assignTaskToUser(@RequestBody @Valid TaskAssignmentRequest taskAssignmentRequest){
        TaskResDTO taskResDTO=taskMapper.mapToDTO(taskService.assignTask(taskAssignmentRequest));
        return ResponseEntity.ok(taskResDTO);
    }




}
