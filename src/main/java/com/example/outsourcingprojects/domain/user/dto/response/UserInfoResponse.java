package com.example.outsourcingprojects.domain.user.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserInfoResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                roleToString(user.getRole()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public static String roleToString(Long roleNum) {
        if (roleNum == 20L) return "USER";
        if (roleNum == 10L) return "ADMIN";
        return "UNKNOWN";
    }

}
