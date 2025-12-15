package com.example.outsourcingprojects.domain.dashboard.dto;

import com.example.outsourcingprojects.domain.entity.DashBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DashBoardDTO {

    private final Long totalTasks;
    private final Long completedTasks;
    private final Long inProgressTasks;
    private final Long todoTasks;
    private final Long overdueTasks;
    private final Double teamProgress;
    private final Long myTasksToday;
    private final Double completionRate;

    public static DashBoardDTO from(DashBoard dashBoard, Long myTasksToday) {
        return new DashBoardDTO(dashBoard.getTotalTasks(),
                dashBoard.getCompletedTasks(),
                dashBoard.getInProgressTasks(),
                dashBoard.getTodoTasks(),
                dashBoard.getOverdueTasks(),
                dashBoard.getTeamProgress(),
                myTasksToday,
                dashBoard.getCompletionRate());
    }
}
