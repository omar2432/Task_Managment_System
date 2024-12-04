package com.hitachi.userService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "\"user\"") // Note the use of double quotes
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public User(String name, String email, int age, UserQualification qualification) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.qualification = qualification;
    }
}
