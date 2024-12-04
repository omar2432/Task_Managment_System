package com.hitachi.taskService.exchange.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {
    public enum UserQualification {
        Doctor,
        Engineer,
        Pilot,
        None
    }

    private Long id;

    private String name;

    private String email;

    private int age;

    private UserQualification qualification;
}
