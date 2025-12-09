package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TaskStatusType {

    TODO(10L),
    IN_PROGRESS(20L),
    DONE(30L);

    private final long statusNum;

}
