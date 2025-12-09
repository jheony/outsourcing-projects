package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.User;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateTaskRequestDto {

    private Long assignedId;

    private String title;

    private String description;

    private Long priority;

    private Long status;

    private LocalDateTime dueDate;
}