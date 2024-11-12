package com.hitachi.userService.common;

public class MessageConstants {
    // User-related messages
    public static final String USER_NOT_FOUND = "User not found with the ID ";
    public static final String USER_NOT_FOUND_WITH_ID = "User with id %d not found.";

    // Route paths
    public static final String API_BASE_PATH = "/users";
    public static final String GET_USER_BY_ID_PATH = "/{id}";
    public static final String CREATE_USER_PATH = "";
    public static final String UPDATE_USER_PATH = "/{id}";
    public static final String DELETE_USER_PATH = "/{id}";
}
