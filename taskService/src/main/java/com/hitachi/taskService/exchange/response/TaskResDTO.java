package com.hitachi.taskService.exchange.response;

import com.hitachi.taskService.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TaskResDTO {

    private Long id;
    private Long assignedUserId;
    private Task.Status status= Task.Status.NOTASSIGNED;
    private LocalDateTime completedAt;

}