package com.example.outsourcingprojects.domain.dashboard.dto;

import com.example.outsourcingprojects.common.entity.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TaskSummaryDTO {
    private final Long id;
    private final String title;
    private final Long status;
    private final Long priority;
    private final LocalDateTime dueDate;

    public static TaskSummaryDTO from(Task task){
        return new TaskSummaryDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }
}