package com.example.outsourcingprojects.domain.auth.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.auth.dto.request.LoginRequest;
import com.example.outsourcingprojects.domain.auth.dto.request.VerifyPasswordRequest;
import com.example.outsourcingprojects.domain.auth.dto.response.LoginResponse;
import com.example.outsourcingprojects.domain.auth.dto.response.VerifyPasswordResponse;
import com.example.outsourcingprojects.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<GlobalResponse<LoginResponse>> loginHandler(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(GlobalResponse.success("로그인 성공", response));
    }

    @PostMapping("/verify-password")
    public GlobalResponse<VerifyPasswordResponse> verifyPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody VerifyPasswordRequest request
    ) {
    //메서드명 약속한대로 Handler 작성해주셔야 합니다.
        String username = userDetails.getUsername();

        VerifyPasswordResponse response = authService.verifyPasswordHandler(username, request);

        return GlobalResponse.success("비밀번호가 확인되었습니다.", response);
    }
}
