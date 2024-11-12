package com.hitachi.taskService.common;

public class MessageConstants {
    // Task-related messages
    public static final String TASK_NOT_FOUND = "Task not found with the ID ";
    public static final String TASK_NOT_FOUND_WITH_ID = "Task with id %d not found.";

    // User-related messages
    public static final String USER_NOT_FOUND = "User not found with the ID ";
    public static final String USER_FOUND = "User with the ID ";

    // Route paths
    public static final String API_BASE_PATH = "/tasks";
    public static final String GET_TASK_BY_ID_PATH = "/{id}";
    public static final String CREATE_TASK_PATH = "";
    public static final String UPDATE_TASK_PATH = "/{id}";
    public static final String DELETE_TASK_PATH = "/{id}";
    public static final String ASSIGN_TASK_PATH = "/assign";
}
