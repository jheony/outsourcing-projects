package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriorityType {

    LOW(10L),
    MEDIUM(20L),
    HIGH(30);

    private final long priorityNum;

}