package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.UserRoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TeamMemberResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

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
