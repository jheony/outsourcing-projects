package com.example.outsourcingprojects.domain.dashboard.service;

import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.domain.dashboard.repository.DashBoardRepository;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.DashBoardDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.GetTaskSummaryResponse;
import com.example.outsourcingprojects.domain.dashboard.dto.TaskSummaryDTO;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final DashBoardRepository dashBoardRepository;

    public GetTaskSummaryResponse getTaskSummaries(Long userId) {

        Page<Task> upcomingTasks = dashBoardRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.TODO.getStatusNum());
        Page<Task> todayTasks = dashBoardRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.IN_PROGRESS.getStatusNum());
        Page<Task> overdueTasks = dashBoardRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.DONE.getStatusNum());

        List<TaskSummaryDTO> upcomingSummaryTasks = upcomingTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> todaySummaryTasks = todayTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> overdueSummaryTasks = overdueTasks.stream().map(TaskSummaryDTO::from).toList();

        return GetTaskSummaryResponse.from(upcomingSummaryTasks, todaySummaryTasks, overdueSummaryTasks);

    }

    public DashBoardDTO getDashBoard(Long userId) {
        List<Tuple> statusTask = dashBoardRepository.countTasksByStatus();

        //Todo(10L) > InProgress(20L) > Done(30L)
        Long todoTasks = 0L;
        Long inProgressTasks = 0L;
        Long completedTasks = 0L;

        BigDecimal bd;

        for (Tuple tuple : statusTask) {
            Long status = tuple.get(QTask.task.status);
            Long count = tuple.get(QTask.task.id.count());

            if (status == null || status == 0L) {
                continue;
            }
            if (status == TaskStatusType.TODO.getStatusNum()) {
                todoTasks = (count != null) ? count : 0L;
            }
            if (status == TaskStatusType.IN_PROGRESS.getStatusNum()) {
                inProgressTasks = (count != null) ? count : 0L;
            }
            if (status == TaskStatusType.DONE.getStatusNum()) {
                completedTasks = (count != null) ? count : 0L;
            }
        }
        Long totalTasks = todoTasks + inProgressTasks + completedTasks;

        Long overdueTasks = dashBoardRepository.countOverdueTask();

        Long myTasksToday = dashBoardRepository.countMyTaskToday(userId);

        Double teamProgress = ((completedTasks.doubleValue() + inProgressTasks.doubleValue()) / totalTasks.doubleValue()) * 100.0;
        bd = new BigDecimal(teamProgress).setScale(2, RoundingMode.HALF_UP);
        teamProgress = bd.doubleValue();

        Double completionRate = completedTasks.doubleValue() / totalTasks.doubleValue() * 100.0;
        bd = new BigDecimal(completionRate).setScale(2, RoundingMode.HALF_UP);
        completionRate = bd.doubleValue();;

        return new DashBoardDTO(totalTasks, completedTasks, inProgressTasks, todoTasks, overdueTasks, teamProgress, myTasksToday, completionRate);
    }


    public List<DailyTaskDTO> getWeeklyTasks(Long userId) {
        List<DailyTaskDTO> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            result.add(dashBoardRepository.getDailyTask(i, userId));
        }

        return result;
    }
}