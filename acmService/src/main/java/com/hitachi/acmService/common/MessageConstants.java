package com.hitachi.acmService.common;

public class MessageConstants {

    // Route paths
    public static final String API_BASE_PATH = "/keycloakUsers";
    public static final String GET_KEYCLOAK_USER_BY_ID_PATH = "/{userId}";
    public static final String CREATE_KEYCLOAK_USER_PATH = "/register";
    public static final String UPDATE_KEYCLOAK_USER_PATH = "/{userId}";
    public static final String DELETE_KEYCLOAK_USER_PATH = "/{userId}";
    public static final String LOGIN_PATH = "/login";

    // User-related messages
    public static final String USER_NOT_FOUND = "User not found with the ID ";
    public static final String USER_FOUND = "User with the ID ";
    public static final String USER_NOT_CREATED = "Failed to create user: ";
    public static final String USER_CREATED = "User created successfully";
    public static final String USER_UPDATED = "User updated successfully";
    public static final String USER_DELETED = "User deleted successfully";
    public static final String LOGIN_FAILED = "Failed to obtain access token: ";

}
