package com.example.outsourcingprojects.domain.auth.dto;

import com.example.outsourcingprojects.common.model.UserRoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserDTO {

    private Long userId;
    private String username;
    private Long userRole;
}
