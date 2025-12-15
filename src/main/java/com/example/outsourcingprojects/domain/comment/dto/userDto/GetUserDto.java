package com.example.outsourcingprojects.domain.comment.dto.userDto;

import com.example.outsourcingprojects.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserDto {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;
    private final String role;

    public static GetUserDto from(User user) {
        return new GetUserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                roleToString(user.getRole())
        );
    }

    public static String roleToString(Long roleNum) {
        if (roleNum == 20L) return "USER";
        if (roleNum == 10L) return "ADMIN";
        return "UNKNOWN";
    }

}
