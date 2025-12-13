package com.example.outsourcingprojects.common.util.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskCsvDTO {
    private Long id;
    private Long assignee_id;
    private String title;
    private String description;
    private Long priority;
    private Long status;
    private LocalDateTime dueDate;
}
