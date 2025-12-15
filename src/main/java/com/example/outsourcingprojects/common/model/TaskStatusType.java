package com.example.outsourcingprojects.common.model;

import com.example.outsourcingprojects.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.outsourcingprojects.common.exception.ErrorCode.TYPE_NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum TaskStatusType {

    TODO(10L),
    IN_PROGRESS(20L),
    DONE(30L);

    private final long statusNum;

    public static TaskStatusType toType(Long value) {
        if (value == null) throw new CustomException(TYPE_NOT_FOUND);

        for (TaskStatusType type : TaskStatusType.values()) {
            if (type.getStatusNum() == value) {
                return type;
            }
        }
        throw new CustomException(TYPE_NOT_FOUND);
    }
}
