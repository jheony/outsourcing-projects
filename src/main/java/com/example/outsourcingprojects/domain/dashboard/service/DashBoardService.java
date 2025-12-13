package com.example.outsourcingprojects.domain.dashboard.service;

import com.example.outsourcingprojects.common.aop.TrackTime;
import com.example.outsourcingprojects.common.entity.DashBoard;
import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.DashBoardDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.GetTaskSummaryResponse;
import com.example.outsourcingprojects.domain.dashboard.dto.TaskSummaryDTO;
import com.example.outsourcingprojects.domain.dashboard.repository.DashBoardRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final TaskRepository taskRepository;
    private final DashBoardRepository dashBoardRepository;

    @TrackTime
    @Transactional(readOnly = true)
    public GetTaskSummaryResponse getTaskSummaries(Long userId) {

        Page<Task> upcomingTasks = taskRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.TODO.getStatusNum());
        Page<Task> todayTasks = taskRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.IN_PROGRESS.getStatusNum());
        Page<Task> overdueTasks = taskRepository.findAllByAssigneeIdAndStatus(userId, TaskStatusType.DONE.getStatusNum());

        List<TaskSummaryDTO> upcomingSummaryTasks = upcomingTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> todaySummaryTasks = todayTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> overdueSummaryTasks = overdueTasks.stream().map(TaskSummaryDTO::from).toList();

        return GetTaskSummaryResponse.from(upcomingSummaryTasks, todaySummaryTasks, overdueSummaryTasks);

    }

    @Scheduled(fixedRate = 1000 * 60 * 10)
    @TrackTime
    @Transactional
    public void refreshDashBoard() {
        List<Tuple> statusTask = taskRepository.countTasksByStatus();

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

        Long overdueTasks = taskRepository.countOverdueTask();

        Double teamProgress = ((completedTasks.doubleValue() + inProgressTasks.doubleValue()) / totalTasks.doubleValue()) * 100.0;
        bd = new BigDecimal(teamProgress).setScale(2, RoundingMode.HALF_UP);
        teamProgress = bd.doubleValue();

        Double completionRate = completedTasks.doubleValue() / totalTasks.doubleValue() * 100.0;
        bd = new BigDecimal(completionRate).setScale(2, RoundingMode.HALF_UP);
        completionRate = bd.doubleValue();
        ;

        DashBoard dashBoard = DashBoard.from(totalTasks, completedTasks, inProgressTasks, todoTasks, overdueTasks, teamProgress, completionRate);

        dashBoardRepository.save(dashBoard);
    }

    @TrackTime
    @Transactional(readOnly = true)
    public List<DailyTaskDTO> getWeeklyTasks(Long userId) {
        List<DailyTaskDTO> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            result.add(taskRepository.getDailyTask(i, userId));
        }

        return result;
    }

    @TrackTime
    @Transactional(readOnly = true)
    public DashBoardDTO getDashBoard(Long userId) {
        DashBoard dashBoard = dashBoardRepository.findLatestDashBoard();
        Long todayMyTasks = taskRepository.countMyTaskToday(userId);

        return DashBoardDTO.from(dashBoard, todayMyTasks);
    }
}