package com.example.outsourcingprojects.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    //응답객체의 필드는 final을 붙이는게 좋을지 아닐지 염두해보시기 바랍니다.
    //응답객체가 final로 바뀌면 상단의 어노테이션도 변경이 필요해보입니다.
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}