package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    TASK_CREATED(101L, "TASK", "작업 생성", "POST", "새 작업 '%s'를 생성했습니다."),
    TASK_UPDATED(102L, "TASK", "작업 수정", "PUT", "작업 '%s' 정보를 수정했습니다."),
    TASK_DELETED(103L, "TASK", "작업 삭제", "DELETE", "작업 '%s'를 삭제했습니다."),
    TASK_STATUS_CHANGED(104L, "TASK", "작업 상태 변경", "PATCH", "작업 상태를 '%s'에서 '%s'으로 변경했습니다."),
    COMMENT_CREATED(201L, "COMMENT", "댓글 생성", "POST", "작업 '%s'에 댓글을 작성했습니다."),
    COMMENT_UPDATED(202L, "COMMENT", "댓글 수정", "PUT", "댓글을 수정했습니다."),
    COMMENT_DELETED(203L, "COMMENT", "댓글 삭제", "DELETE", "댓글을 삭제했습니다.");

    private final long activityNum;
    private final String targetType;
    private final String action;
    private final String method;
    private final String description;

    public static ActivityType toType(Long value) {

        for (ActivityType type : ActivityType.values()) {
            if (type.getActivityNum() == value) {
                return type;
            }
        }
        return null;
    }

    public static ActivityType methodToType(String action, String method) {

        for (ActivityType type : ActivityType.values()) {
            if (type.getAction().startsWith(action) && type.getMethod().equals(method)) {
                return type;
            }
        }
        return null;
    }

    public String getFormatDescription(String taskTitle) {
        return String.format(this.getDescription(), taskTitle);
    }

    public String getFormatDescription(String curStatus, String newStatus) {
        return String.format(this.getDescription(), curStatus, newStatus);
    }
}
