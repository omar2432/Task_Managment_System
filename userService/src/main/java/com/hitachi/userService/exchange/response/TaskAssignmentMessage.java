package com.hitachi.userService.exchange.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TaskAssignmentMessage {
    private long taskId;
    private long userId;
}