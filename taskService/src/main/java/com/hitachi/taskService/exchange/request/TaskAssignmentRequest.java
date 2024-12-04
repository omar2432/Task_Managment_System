package com.hitachi.taskService.exchange.request;

public class TaskAssignmentRequest {
    private long taskId;
    private long userId;

    public TaskAssignmentRequest(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public long getUserId() {
        return userId;
    }
}
