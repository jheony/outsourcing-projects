package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.UserRoleType;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamMemberResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public TeamMemberResponseDto(Long id, String username, String email, String name, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    private static String roleToString(Long roleNum) {
        if (roleNum == 20L) return UserRoleType.USER.name();
        if (roleNum == 10L) return UserRoleType.ADMIN.name();
        return null;
    }

    public static TeamMemberResponseDto from(User user) {
        return new TeamMemberResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                roleToString(user.getRole()),

                user.getCreatedAt()
        );
    }

}
