package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateTaskResponseDto {

    private Long id;

    private long assigneeId;

    private String title;

    private String description;

    private Long priority;

    private Long status;

    private LocalDateTime dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;



}
