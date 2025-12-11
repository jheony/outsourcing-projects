package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.UserRoleType;
import jakarta.validation.constraints.Null;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TeamMemberResponseDto {
    //response 응답객체의 필드는 final로 지정하는것에 대해 고민해보시기 바랍니다.
    //final로 지정하면 상단의 생성자 어노테이션도 변경되어야 합니다.

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final String role;
    private final LocalDateTime createdAt;

    public TeamMemberResponseDto(Long id, String username, String email, String name, String role, LocalDateTime createdAt) {
        //생성자를 어노테이션으로 작성하기로 약속되어 있었는데 따로 작성해주셨네요. 다시한번 확인 부탁드립니다.
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
        //필요없는 개행은 삭제하고 정리해서 푸시 하셔야합니다.
        //파일 저장 전에, ctr + alt + L을 습관화 합시다.
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
