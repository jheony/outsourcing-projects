package com.example.outsourcingprojects.domain.task.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateTaskStatusResponse {

    private final Long id;
    private final String username;
    private final String name;
}