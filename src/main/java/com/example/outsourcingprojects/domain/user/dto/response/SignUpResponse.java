package com.example.outsourcingprojects.domain.user.dto.response;

import com.example.outsourcingprojects.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public static SignUpResponse from(User user) {
        return new SignUpResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                roleToString(user.getRole()),
                user.getCreatedAt()
        );
    }

    public static String roleToString(Long roleNum) {
        if (roleNum == 20L) return "USER";
        if (roleNum == 10L) return "ADMIN";
        return "UNKNOWN";
    }

}
