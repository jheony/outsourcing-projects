package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.domain.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateTaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final Long assigneeId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static UpdateTaskResponse from(Task task) {
        return new UpdateTaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                TaskStatusType.toType(task.getStatus()).name(),
                PriorityType.toType(task.getPriority()).name(),
                task.getAssignee().getId(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }
}