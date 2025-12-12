package com.example.outsourcingprojects.domain.activitylog.dto;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.model.ActivityType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MyActivityLogResponse {

    private final Long id;
    private final Long userId;
    private final UserActivityLogResponse user;
    private final String action;
    private final String targetType;
    private final Long targetId;
    private final String description;
    private final LocalDateTime createdAt;

    public static MyActivityLogResponse from(ActivityLog activityLog, UserActivityLogResponse userResponse) {
        return new MyActivityLogResponse(
                activityLog.getId(),
                activityLog.getUser().getId(),
                userResponse,
                ActivityType.toType(activityLog.getType()).getAction(),
                ActivityType.toType(activityLog.getType()).getTargetType(),
                activityLog.getTaskId(),
                activityLog.getDescription(),
                activityLog.getTimestamp()
        );
    }
}
