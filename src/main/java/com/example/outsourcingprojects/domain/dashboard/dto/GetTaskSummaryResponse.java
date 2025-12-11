package com.example.outsourcingprojects.domain.dashboard.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetTaskSummaryResponse {

    private final List<TaskSummaryDTO> upcomingTasks;
    private final List<TaskSummaryDTO> todayTasks;
    private final List<TaskSummaryDTO> overdueTasks;


    public static GetTaskSummaryResponse from(List<TaskSummaryDTO> upcomingTasks, List<TaskSummaryDTO> todayTasks, List<TaskSummaryDTO> overdueTasks) {
        return new GetTaskSummaryResponse(
                upcomingTasks,
                todayTasks,
                overdueTasks
        );
    }
}