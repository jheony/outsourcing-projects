package com.example.outsourcingprojects.domain.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponse {
    private final List<UserSummaryResponse> userlist;

    public UserListResponse(List<UserSummaryResponse> userlist) {
        this.userlist = userlist;
    }
}
