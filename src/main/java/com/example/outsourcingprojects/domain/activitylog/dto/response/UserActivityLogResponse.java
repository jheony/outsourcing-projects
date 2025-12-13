package com.example.outsourcingprojects.domain.activitylog.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserActivityLogResponse {

    private final Long id;
    private final String username;
    private final String name;

    public static UserActivityLogResponse from(User user) {
        return new UserActivityLogResponse(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }
}
