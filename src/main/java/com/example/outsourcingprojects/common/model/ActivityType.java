package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActivityType {

    TASK_CREATED(101L),
    TASK_UPDATED(102L),
    TASK_DELETED(103L),
    TASK_STATUS_CHANGED(104L),
    COMMENT_CREATED(201L),
    COMMENT_UPDATED(202L),
    COMMENT_DELETED(203L);

    private final long activityNum;

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
