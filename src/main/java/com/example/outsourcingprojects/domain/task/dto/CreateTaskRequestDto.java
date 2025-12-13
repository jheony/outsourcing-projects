package com.example.outsourcingprojects.domain.task.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTaskRequestDto {

    private Long assigneeId;
    private String title;
    private String description;
    private String priority;
    private LocalDateTime dueDate;
}