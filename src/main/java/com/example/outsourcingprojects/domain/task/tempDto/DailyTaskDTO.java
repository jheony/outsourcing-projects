package com.example.outsourcingprojects.domain.task.tempDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DailyTaskDTO {
    private final Character name;
    private final Long tasks;
    private final Long completed;
    private final String date;
}

