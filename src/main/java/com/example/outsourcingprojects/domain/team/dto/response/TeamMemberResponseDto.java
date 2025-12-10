package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamMemberResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final Long role;
    private final LocalDateTime createdAt;

    public TeamMemberResponseDto(Long id, String username, String email, String name, Long role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getRole(Long roleNum) {
        if (this.role == 20L) return "USER";
        if (this.role == 10L) return "ADMIN";
        return "UNKNOWN";
    }

    public static TeamMemberResponseDto from(User user) {
        return new TeamMemberResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }


}
