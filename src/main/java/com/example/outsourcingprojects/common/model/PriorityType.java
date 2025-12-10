package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PriorityType {

    LOW(10L),
    MEDIUM(20L),
    HIGH(30);

    private final long priorityNum;

    public static PriorityType toType(Long value) throws Exception {
        if (value == null) throw new Exception();

        for (PriorityType type : PriorityType.values()) {
            if (type.getPriorityNum() == value) {
                return type;
            }
        }

        throw new Exception();
    }
}