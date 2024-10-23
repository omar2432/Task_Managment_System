package com.hitachi.userService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "\"user\"") // Note the use of double quotes
public class User {

    public enum UserQualification {
        Doctor,
        Engineer,
        Pilot,
        None
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    @Email
    private String email;

    private int age;

    private UserQualification qualification;

    public User() {
    }

    public User(String name, String email, int age, UserQualification qualification) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UserQualification getQualification() {
        return qualification;
    }

    public void setQualification(UserQualification qualification) {
        this.qualification = qualification;
    }
}
