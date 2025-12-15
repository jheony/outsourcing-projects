package com.example.outsourcingprojects.common.model;

import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriorityType {

    LOW(10L),
    MEDIUM(20L),
    HIGH(30);

    private final long priorityNum;

    public static PriorityType toType(Long value) {
        if (value == null) throw new CustomException(ErrorCode.TYPE_NOT_FOUND);

        for (PriorityType type : PriorityType.values()) {
            if (type.getPriorityNum() == value) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.TYPE_NOT_FOUND);
    }
}