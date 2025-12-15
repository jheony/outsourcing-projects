package com.example.outsourcingprojects.domain.dashboard.dto;

import com.example.outsourcingprojects.domain.entity.Task;
import com.example.outsourcingprojects.domain.user.dto.response.UserSummaryResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TaskSummaryDTO {
    private final Long id;
    private final String title;
    private final Long status;
    private final UserSummaryResponse assignee;
    private final Long priority;
    private final LocalDateTime dueDate;

    public static TaskSummaryDTO from(Task task){
        return new TaskSummaryDTO(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                UserSummaryResponse.from(task.getAssignee()),
                task.getPriority(),
                task.getDueDate()
        );
    }
}