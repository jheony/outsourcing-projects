package com.example.outsourcingprojects.domain.task.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateTaskRequestDto {

    private Long assigneeId;

    private String title;

    private String description;

    private String priority;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private OffsetDateTime dueDate;
}