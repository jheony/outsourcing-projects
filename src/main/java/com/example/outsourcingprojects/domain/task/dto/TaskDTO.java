package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.domain.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskDTO {

    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final Long assigneeId;
    private final UpdateTaskStatusResponse assignee;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static TaskDTO from(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                TaskStatusType.toType(task.getStatus()).name(),
                PriorityType.toType(task.getPriority()).name(),
                task.getAssignee().getId(),
                new UpdateTaskStatusResponse(
                        task.getAssignee().getId(),
                        task.getAssignee().getUsername(),
                        task.getAssignee().getName()
                ),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }
}