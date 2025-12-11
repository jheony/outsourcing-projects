package com.example.outsourcingprojects.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponse {
    //생성자 어노테이션을 고민해보시면 좋을것 같습니다.
    //final이 붙은 필드를 모두 가지는 생성자는 @RequiredArgsConstructor 입니다.
    //생성자 관련 어노테이션들에 대해 공부해보시기 바랍니다.

    //해당 응답을 정적 팩터리 메서드로 관리해주세요.
    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final Long role;
    private final LocalDateTime createdAt;

    public SignUpResponse(Long id, String username, String email, String name, Long role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    @JsonGetter("role")
    public String roleToString() {
        if (this.role == 20L) return "USER";
        if (this.role == 10L) return "ADMIN";
        return "UNKNOWN";
    }

}
