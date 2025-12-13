package com.example.outsourcingprojects.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskAssigneeReponse {

    private final Long id;
    private final String username;
    private final String name;
}
