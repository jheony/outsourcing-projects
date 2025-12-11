package com.example.outsourcingprojects.domain.user.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchUserResponse {
    private final Long id;
    private final String name;
    private final String username;

    public static SearchUserResponse from(User user) {
        return new SearchUserResponse(user.getId(), user.getName(), user.getUsername());
    }
}
