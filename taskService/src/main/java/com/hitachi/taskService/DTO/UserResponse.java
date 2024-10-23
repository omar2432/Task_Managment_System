package com.hitachi.taskService.DTO;

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

    public UserResponse(Long id, String name, String email, int age, UserQualification qualification) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.qualification = qualification;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public UserQualification getQualification() {
        return qualification;
    }
}
