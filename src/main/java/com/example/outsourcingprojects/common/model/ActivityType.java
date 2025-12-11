package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    TASK_CREATED(101L, "TASK", "작업 생성"),
    TASK_UPDATED(102L, "TASK", "작업 수정"),
    TASK_DELETED(103L, "TASK", "작업 삭제"),
    TASK_STATUS_CHANGED(104L, "TASK", "작업 상태 변경"),
    COMMENT_CREATED(201L, "COMMENT", "댓글 생성"),
    COMMENT_UPDATED(202L, "COMMENT", "댓글 수정"),
    COMMENT_DELETED(203L, "COMMENT", "댓글 삭제");

    private final long activityNum;
    private final String targetType;
    private final String action;

    public static ActivityType toType(Long value) throws Exception {
        if (value == null) throw new Exception();

        for (ActivityType type : ActivityType.values()) {
            if (type.getActivityNum() == value) {
                return type;
            }
        }

        throw new Exception();
    }
}
