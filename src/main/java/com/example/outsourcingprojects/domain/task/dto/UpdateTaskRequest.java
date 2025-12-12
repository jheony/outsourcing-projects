package com.example.outsourcingprojects.domain.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateTaskRequest {

    private String title;
    private String description;
    private String priority;
    private String status;
    private Long assigneeId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dueDate;
}

