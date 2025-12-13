package com.example.outsourcingprojects.common.model;

import com.example.outsourcingprojects.common.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.example.outsourcingprojects.common.exception.ErrorCode.TYPE_NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {

    ADMIN(10L),
    USER(20L);

    private final long roleNum;

    public static UserRoleType toType(Long value) throws CustomException {
        if (value == null) throw new CustomException(TYPE_NOT_FOUND);

        for (UserRoleType type : UserRoleType.values()) {
            if (type.getRoleNum() == value) {
                return type;
            }
        }

        throw new CustomException(TYPE_NOT_FOUND);
    }

    public static UserRoleType strToType(String value) throws CustomException {
        if (value == null) throw new CustomException(TYPE_NOT_FOUND);

        for (UserRoleType type : UserRoleType.values()) {
            if (type.name().equals(value)) {
                return type;
            }
        }

        throw new CustomException(TYPE_NOT_FOUND);
    }
}
