package com.example.outsourcingprojects.domain.task.dto;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchTaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatusType status;

    public static SearchTaskResponse from(Task task) {
        return new SearchTaskResponse(task.getId(), task.getTitle(), task.getDescription(), TaskStatusType.toType(task.getStatus()));
    }
}
