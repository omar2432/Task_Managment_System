package com.hitachi.taskService.exchange.request;

import com.hitachi.taskService.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TaskReqDTO {

    private String description;
    private Long assignedUserId;
    private Task.Status status= Task.Status.NOTASSIGNED;
    private LocalDateTime completedAt;

}
