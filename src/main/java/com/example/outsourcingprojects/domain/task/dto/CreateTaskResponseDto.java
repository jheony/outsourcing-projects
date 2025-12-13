package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

//필요없는 import문은 반드시 제거해주세요. ok

@Getter
@AllArgsConstructor
public class CreateTaskResponseDto {

    private final Long id;
    private final Long assigneeId;
    private final String title;
    private final String description;
    private final String priority;
    private final String status;
    private final LocalDateTime dueDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CreateTaskResponseDto from(Task task) {

        return new CreateTaskResponseDto(
                task.getId(),
                task.getAssignee().getId(),
                task.getTitle(),
                task.getDescription(),
                PriorityType.toType(task.getPriority()).name(),
                TaskStatusType.toType(task.getStatus()).name(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}