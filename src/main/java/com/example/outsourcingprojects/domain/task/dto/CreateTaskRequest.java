package com.example.outsourcingprojects.domain.task.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateTaskRequest {

    private Long assigneeId;
    private String title;
    private String description;
    private String priority;
    private LocalDateTime dueDate;
}