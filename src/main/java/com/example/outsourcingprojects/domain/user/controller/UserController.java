package com.example.outsourcingprojects.domain.user.controller;

import com.example.outsourcingprojects.common.aop.Loggable;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.user.dto.response.SignUpResponse;
import com.example.outsourcingprojects.domain.user.dto.response.UserInfoResponse;
import com.example.outsourcingprojects.domain.user.dto.response.UserListResponse;
import com.example.outsourcingprojects.domain.user.dto.request.UpdateRequest;
import com.example.outsourcingprojects.domain.user.dto.response.UpdateResponse;
import com.example.outsourcingprojects.domain.user.service.UserService;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    //사용하지 않는 어노테이션이 남아있습니다. 확인하고 제거해주세요.
    //Controller의 메서드명의 끝에 Handler를 붙여주기로 약속했습니다. 확인하고 수정해주세요.
    private final UserService userService;

    //회원가입
    @Loggable
    @PostMapping
    public GlobalResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse userResponse = userService.signUpUser(request);
        return GlobalResponse.success("회원가입이 완료되었습니다", userResponse);
    }

    // 사용자 정보 조회
    @GetMapping("/{id}")
    public GlobalResponse<UserInfoResponse> getUserInfo(@PathVariable Long id) {
        UserInfoResponse userInfoResponse = userService.info(id);
        return GlobalResponse.success("[사용자" + id + "의 정보 조회가 완료 되었습니다]", userInfoResponse);
    }

    // 사용자 목록 조회
    @GetMapping
    public GlobalResponse<UserListResponse> getUsersInfo() {
        UserListResponse allUsersInfo = userService.usersInfo();
        return GlobalResponse.success("사용자 목록 조회 성공", allUsersInfo);

    }

    // 사용자 정보 수정
    @PutMapping("{id}")
    public GlobalResponse<UpdateResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateRequest request) {
        UpdateResponse updateUserResponse = userService.update(id, request);
        return GlobalResponse.success("사용자 정보가 수정되었습니다.", updateUserResponse);
    }

    // 회원 탈퇴


    // 추가 가능한 사용자 조회

}

