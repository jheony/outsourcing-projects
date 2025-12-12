package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskListResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final Long assigneeId;
    private final Assignee assignee;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime dueDate;

    // 정적 팩토리 메서드
    public static TaskListResponseDto from(Task task) {
        return new TaskListResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                TaskStatusType.toType(task.getStatus()).name(),
                PriorityType.toType(task.getPriority()).name(),
                task.getAssignee().getId(),
                new Assignee(
                        task.getAssignee().getId(),
                        task.getAssignee().getUsername(),
                        task.getAssignee().getName()
                ),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getDueDate()
        );
    }

    // 내부 클래스
    @Getter
    @AllArgsConstructor
    public static class Assignee {
        private final Long id;
        private final String username;
        private final String name;
    }
}