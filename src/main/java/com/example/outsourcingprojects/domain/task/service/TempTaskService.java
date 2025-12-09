package com.example.outsourcingprojects.domain.task.service;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.domain.task.repository.TempTaskRepository;
import com.example.outsourcingprojects.domain.task.tempDto.GetTaskSummaryResponse;
import com.example.outsourcingprojects.domain.task.tempDto.TaskSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TempTaskService {

    private final TempTaskRepository tempTaskRepository;

    public GetTaskSummaryResponse GetTaskSummaries(Long userId) {

        final Long UPCOMING_STATUS = 1L;
        final Long TODAY_STATUS = 2L;
        final Long OVERDUE_STATUS = 3L;

        Page<Task> upcomingTasks = tempTaskRepository.findAllByAssigneeIdAndStatus(userId, UPCOMING_STATUS);
        Page<Task> todayTasks = tempTaskRepository.findAllByAssigneeIdAndStatus(userId, TODAY_STATUS);
        Page<Task> overdueTasks = tempTaskRepository.findAllByAssigneeIdAndStatus(userId, OVERDUE_STATUS);

        List<TaskSummaryDTO> upcomingSummaryTasks = upcomingTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> todaySummaryTasks = todayTasks.stream().map(TaskSummaryDTO::from).toList();
        List<TaskSummaryDTO> overdueSummaryTasks = overdueTasks.stream().map(TaskSummaryDTO::from).toList();

        return GetTaskSummaryResponse.from(upcomingSummaryTasks, todaySummaryTasks, overdueSummaryTasks);

    }
}