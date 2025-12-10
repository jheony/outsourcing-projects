package com.example.outsourcingprojects.domain.task.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateTaskRequestDto {

    private Long assigneeId;

    private String title;

    private String description;

    private Long priority;

    private Long status;

    private LocalDateTime dueDate;
}