package com.example.outsourcingprojects.domain.activitylog.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ActivityLogResponse {
    private final Long id;
    private final Long userId;
    private final UserActivityLogResponse user;
    private final String action;
    private final String targetType;
    private final Long targetId;
    private final String description;
    private final LocalDateTime createdAt;

//    public static ActivityLogResponse from(ActivityLog activityLog, UserActivityLogResponse userActivityLogResponse) {
//        return new ActivityLogResponse(
//                activityLog.getId(),
//                activityLog.getUsername(),
//                activityLog.getEmail(),
//                activityLog.getName(),
//                roleToString(activityLog.getRole()),
//                activityLog.getCreatedAt()
//        );
//    }
}
