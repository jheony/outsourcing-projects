package com.example.outsourcingprojects.domain.auth.dto;

import com.example.outsourcingprojects.common.model.UserRoleType;
//필요하지 않은 import문은 반드시 삭제해주세요.

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserDTO {
    //DTO의 필드는 final을 붙이는게 좋을지 아닐지 염두해보시기 바랍니다.
    //응답객체가 final로 바뀌면 상단의 어노테이션도 변경이 필요해보입니다.
    private Long userId;
    private String username;
    private Long userRole;

    //정적 팩토리 메서드를 작성해 보시는것도 좋을것 같습니다.
}
