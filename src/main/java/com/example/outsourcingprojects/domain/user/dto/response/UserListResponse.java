package com.example.outsourcingprojects.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserListResponse {

    private final List<UserSummaryResponse> userlist;

    public static UserListResponse from(List<UserSummaryResponse> list) {
        return new UserListResponse(list);
    }
}
