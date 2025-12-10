package com.example.outsourcingprojects.domain.auth.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.auth.dto.request.LoginRequest;
import com.example.outsourcingprojects.domain.auth.dto.request.VerifyPasswordRequest;
import com.example.outsourcingprojects.domain.auth.dto.response.LoginResponse;
import com.example.outsourcingprojects.domain.auth.dto.response.VerifyPasswordResponse;
import com.example.outsourcingprojects.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public GlobalResponse<LoginResponse> loginHandler(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return GlobalResponse.success("로그인 성공", response);
    }

    @PostMapping("/verify-password")
    public GlobalResponse<VerifyPasswordResponse> verifyPassword(@Valid @RequestBody VerifyPasswordRequest request) {

        VerifyPasswordResponse response = authService.verifyPasswordHandler(request);

        return GlobalResponse.success("비밀번호가 확인되었습니다.", response);
    }
}
