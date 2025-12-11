package com.example.outsourcingprojects.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AbleUsersListResponse {

    private final List<AbleUserSummaryResponse> user;

    public static AbleUsersListResponse from(List<AbleUserSummaryResponse> users) {
        return new AbleUsersListResponse(users);
    }
}
