package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    TASK_CREATED(101L, "TASK", "팀이 생성", "POST"),
    TASK_UPDATED(102L, "TASK", "작업 수정", "PUT"),
    TASK_DELETED(103L, "TASK", "작업 삭제", "DELETE"),
    TASK_STATUS_CHANGED(104L, "TASK", "작업 상태 변경", "PATCH"),
    COMMENT_CREATED(201L, "COMMENT", "댓글 생성", "POST"),
    COMMENT_UPDATED(202L, "COMMENT", "댓글 수정", "PUT"),
    COMMENT_DELETED(203L, "COMMENT", "댓글 삭제", "DELETE");

    private final long activityNum;
    private final String targetType;
    private final String action;
    private final String method;

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
}
