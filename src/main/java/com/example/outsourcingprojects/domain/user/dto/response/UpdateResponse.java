package com.example.outsourcingprojects.domain.user.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateResponse {

    //생성자 관련 어노테이션들에 대해 공부해보시기 바랍니다.
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final Long role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UpdateResponse from(User user) {
        return new UpdateResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    @JsonGetter("role")
    public String roleToString() {
        if (this.role == 20L) return "USER";
        if (this.role == 10L) return "ADMIN";
        return "UNKNOWN";
    }
}
