package com.example.outsourcingprojects.domain.task.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateTaskRequest {

    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assigneeId;
    private LocalDateTime dueDate;
}