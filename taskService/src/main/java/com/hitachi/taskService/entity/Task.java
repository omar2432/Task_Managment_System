package com.hitachi.taskService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class Task {

    public enum Status {
        ASSIGNED,
        NOTASSIGNED,
        COMPLETED,
        ARCHIVED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long assignedUserId;
    private Status status=Status.NOTASSIGNED;
    private LocalDateTime completedAt;


    public Task(String description, Long assignedUserId, Status status, LocalDateTime completedAt) {
        this.description = description;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.completedAt = completedAt;
    }

}
