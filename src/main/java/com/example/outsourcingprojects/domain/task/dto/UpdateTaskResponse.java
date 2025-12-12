package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateTaskResponse {
    // 속
    //        "success": true,
    //          "message": "작업이 수정되었습니다.",
    private final Long id;
    private final String title;
    private final String description;
    private final String status;
    private final String priority;
    private final Long assigneeId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final LocalDateTime updateAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final LocalDateTime dueDate;

    //        "timestamp":"2024-01-10T00:00:00.000Z"

    // 정적 팩토리 메서드
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

