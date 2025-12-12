package com.example.outsourcingprojects.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class UserInfoDto {

    private final Long id;
    private final String username;
    private final String name;

}
