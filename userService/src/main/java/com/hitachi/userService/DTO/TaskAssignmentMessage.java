package com.hitachi.userService.DTO;

public class TaskAssignmentMessage {
    private long taskId;
    private long userId;

    public TaskAssignmentMessage(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
