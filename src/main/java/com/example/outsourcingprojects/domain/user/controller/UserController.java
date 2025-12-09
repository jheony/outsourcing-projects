package com.example.outsourcingprojects.domain.user.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.user.dto.SignUpResponse;
import com.example.outsourcingprojects.domain.user.dto.UserInfoResponse;
import com.example.outsourcingprojects.domain.user.service.UserService;
import com.example.outsourcingprojects.domain.user.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);
    private final UserService userService;

    //회원가입
    @PostMapping
    public GlobalResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse userResponse = userService.signUpUser(request);
        return GlobalResponse.success("회원가입이 완료되었습니다", userResponse);
    }

    // 사용자 정보 조회
    @GetMapping("/{userId}")
    public GlobalResponse<UserInfoResponse> getUserInfo(@PathVariable Long userId) {
        UserInfoResponse userInfoResponse = userService.info(userId);
        return GlobalResponse.success("[사용자" + userId + "의 정보 조회가 완료 되었습니다]", userInfoResponse);
    }

    // 사용자 목록 조회

    // 사용자 정보 수정

    // 회원 탈퇴(영구 삭제)

    // 추가 가능한 사용자 조회

}

