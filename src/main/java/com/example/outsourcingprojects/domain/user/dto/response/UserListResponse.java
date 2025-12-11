package com.example.outsourcingprojects.domain.user.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserListResponse {
    //생성자 어노테이션을 고민해보시면 좋을것 같습니다.
    //final이 붙은 필드를 모두 가지는 생성자는 @RequiredArgsConstructor 입니다.
    //생성자 관련 어노테이션들에 대해 공부해보시기 바랍니다.

    private final List<UserSummaryResponse> userlist;

    public UserListResponse(List<UserSummaryResponse> userlist) {
        this.userlist = userlist;
    }
}
