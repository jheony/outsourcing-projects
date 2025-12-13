package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskRepositoryCustom {
    // 담당자 아이디와 작업 상태를 기준으로 조회
    Page<Task> findAllByAssigneeIdAndStatus(Long assigneeId, Long status);

    // Task 상태에 따른 Task 수 Count
    List<Tuple> countTasksByStatus();
    // 마감일이 지난  Task 수 Count
    Long countOverdueTask();

    Long countMyTaskToday(Long userId);

    DailyTaskDTO getDailyTask(Integer before,Long userId);

    List<SearchTaskResponse> getSearchTasks(String query);
}