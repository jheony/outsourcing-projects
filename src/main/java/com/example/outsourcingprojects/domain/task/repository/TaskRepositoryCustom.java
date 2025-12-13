package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepositoryCustom {

    List<Task> findOverdueTasks(Long assigneeId, LocalDate today);

    List<Task> findTodayTasks(Long assigneeId, LocalDate today);

    List<Task> findUpcomingTasks(Long assigneeId, LocalDate tomorrow);

    // Task 상태에 따른 Task 수 Count
    List<Tuple> countTasksByStatus();

    // 마감일이 지난  Task 수 Count
    Long countOverdueTask();

    Long countMyTaskToday(Long userId);

    DailyTaskDTO getDailyTask(Integer before, Long userId);

    List<SearchTaskResponse> getSearchTasks(String query);

    // 조건 포함 작업 목록 조회
    Page<Task> getAllTaskWithCondition(Long status, String query, Long assigneeId, Pageable pageable);

    Task getTaskById(Long taskId);
}