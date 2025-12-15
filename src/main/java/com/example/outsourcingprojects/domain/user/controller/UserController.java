package com.example.outsourcingprojects.domain.user.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.request.UpdateUserRequest;
import com.example.outsourcingprojects.domain.user.dto.request.VerifyPasswordRequest;
import com.example.outsourcingprojects.domain.user.dto.response.*;
import com.example.outsourcingprojects.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping
    public GlobalResponse<SignUpResponse> signUpHandler(
            @Valid @RequestBody SignUpRequest request
    ) {
        SignUpResponse userResponse = userService.signUpUser(request);

        return GlobalResponse.success("회원가입이 완료되었습니다", userResponse);
    }

    // 사용자 정보 조회
    @GetMapping("/{id}")
    public GlobalResponse<UserInfoResponse> getUserInfoHandler(
            @PathVariable Long id,
            HttpServletRequest userToken
    ) {
        Long userId = (Long) userToken.getAttribute("userId");

        UserInfoResponse userInfoResponse = userService.getUserInfo(id, userId);

        return GlobalResponse.success("[사용자의 정보 조회가 완료 되었습니다]", userInfoResponse);
    }

    // 사용자 목록 조회
    @GetMapping
    public GlobalResponse<List<UserSummaryResponse>> getUsersInfoHandler(
            HttpServletRequest userToken
    ) {
        Long userId = (Long) userToken.getAttribute("userId");

        List<UserSummaryResponse> allUsersInfo = userService.getUsersInfo(userId);

        return GlobalResponse.success("사용자 목록 조회 성공", allUsersInfo);
    }

    // 사용자 정보 수정
    @PutMapping("{id}")
    public GlobalResponse<UpdateResponse> updateUserHandler(
            @PathVariable Long id,
            HttpServletRequest userToken,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        Long userId = (Long) userToken.getAttribute("userId");

        UpdateResponse updateUserResponse = userService.updateUser(userId, id, request);

        return GlobalResponse.success("사용자 정보가 수정되었습니다.", updateUserResponse);
    }

    // 회원 탈퇴
    @DeleteMapping("{id}")
    public GlobalResponse<Void> deleteUserHandler(
            @PathVariable Long id,
            HttpServletRequest userToken
    ) {

        Long userId = (Long) userToken.getAttribute("userId");

        userService.deleteUser(id, userId);

        return GlobalResponse.success("회원 탈퇴가 완료되었습니다.", null);
    }

    // 추가 가능한 사용자 조회
    @GetMapping("/available")
    public GlobalResponse<List<AbleUserSummaryResponse>> getAddableUsersHandler(
            @RequestParam(required = false) Long teamId,
            HttpServletRequest userToken
    ) {
        Long userId = (Long) userToken.getAttribute("userId");

        List<AbleUserSummaryResponse> addableUsers = userService.getAddableUsers(teamId, userId);

        return GlobalResponse.success("추가 가능한 사용자 목록 조회 성공", addableUsers);
    }

    //비밀번호 확인
    @PostMapping("/verify-password")
    public GlobalResponse<VerifyPasswordResponse> verifyPasswordHandler(
            HttpServletRequest userToken,
            @RequestBody VerifyPasswordRequest request
    ) {
        Long userId = (Long) userToken.getAttribute("userId");

        VerifyPasswordResponse response = userService.verifyPassword(userId, request.getPassword());

        if (response.isValid()) {
            return GlobalResponse.success("비밀번호가 확인되었습니다", response);
        } else {
            return GlobalResponse.fail("비밀번호가 올바르지 않습니다", response);
        }
    }
}

