package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StatusUpdateResponseDto {

    private final Long id;
    private final String title;
    private final String description;
    private final Long assigneeId;
    private final String status;
    private final String priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    public static StatusUpdateResponseDto from(Task task) {
        return new StatusUpdateResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAssignee().getId(),
                TaskStatusType.toType(task.getStatus()).name(),
                PriorityType.toType(task.getPriority()).name(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()

        );
    }
}