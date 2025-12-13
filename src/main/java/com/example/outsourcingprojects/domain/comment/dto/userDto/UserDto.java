package com.example.outsourcingprojects.domain.comment.dto.userDto;

import com.example.outsourcingprojects.common.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserDto {

    private final Long id;
    private final String username;
    private final String name;

    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName()
        );
    }

}
