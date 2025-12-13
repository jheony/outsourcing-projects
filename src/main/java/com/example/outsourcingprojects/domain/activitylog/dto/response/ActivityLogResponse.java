package com.example.outsourcingprojects.domain.activitylog.dto.response;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.model.ActivityType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ActivityLogResponse {

    private final Long id;
    private final String type;
    private final Long userId;
    private final UserActivityLogResponse user;
    private final Long taskId;
    private final LocalDateTime timestamp;
    private final String description;

    public static ActivityLogResponse from(ActivityLog activityLog, UserActivityLogResponse userResponse) {
        return new ActivityLogResponse(
                activityLog.getId(),
                ActivityType.toType(activityLog.getType()).name(),
                activityLog.getUser().getId(),
                userResponse,
                activityLog.getTaskId(),
                activityLog.getTimestamp(),
                activityLog.getDescription()
        );
    }
}
