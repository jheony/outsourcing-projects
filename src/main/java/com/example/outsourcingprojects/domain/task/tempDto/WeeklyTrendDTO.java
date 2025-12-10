package com.example.outsourcingprojects.domain.task.tempDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class WeeklyTrendDTO {
    private final List<Character> days;
    private final List<Integer> completed;
    private final List<Integer> created;
}

