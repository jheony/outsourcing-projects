package com.example.outsourcingprojects.domain.task.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateTaskRequest {

    private String title;

    private String description;

    private Long priority;

    private Long status;

    private Long assigneeId;

    private LocalDateTime dueDate;
}




