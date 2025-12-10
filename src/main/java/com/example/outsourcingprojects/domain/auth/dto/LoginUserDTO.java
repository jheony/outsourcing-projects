package com.example.outsourcingprojects.domain.auth.dto;

import com.example.outsourcingprojects.common.model.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserDTO {

    private String username;
    private Long userId;
    private UserRoleType userRole;
}
