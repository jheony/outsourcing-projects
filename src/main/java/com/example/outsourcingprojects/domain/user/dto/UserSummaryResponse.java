package com.example.outsourcingprojects.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSummaryResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final Long role;
    private final LocalDateTime createdAt;

    public UserSummaryResponse(Long id, String username, String email, String name, Long role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    @JsonGetter("role")
    public String getRole() {
        if (this.role == 20L) return "USER";
        if (this.role == 10L) return "ADMIN";
        return "UNKNOWN";
    }
}
