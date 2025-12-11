package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {

    ADMIN(10L),
    USER(20L);

    private final long roleNum;

    public static UserRoleType toType(Long value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException();

        for (UserRoleType type : UserRoleType.values()) {
            if (type.getRoleNum() == value) {
                return type;
            }
        }

        throw new IllegalArgumentException();
    }

    public static UserRoleType strToType(String value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException();

        for (UserRoleType type : UserRoleType.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
