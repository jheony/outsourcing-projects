package com.example.outsourcingprojects.common.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {

    ADMIN(10L),
    USER(20L);

    private final long roleNum;

}
