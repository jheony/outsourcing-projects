package com.example.outsourcingprojects.domain.user.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final Long role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public UpdateResponse(Long id, String username, String email, String name, Long role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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
