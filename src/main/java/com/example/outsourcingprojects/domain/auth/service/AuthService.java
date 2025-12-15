package com.example.outsourcingprojects.domain.auth.service;

import com.example.outsourcingprojects.domain.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.model.UserRoleType;
import com.example.outsourcingprojects.common.util.JwtUtil;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.auth.dto.request.LoginRequest;
import com.example.outsourcingprojects.domain.auth.dto.response.LoginResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 로그인
    @Transactional
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsernameAndDeletedAtIsNull(username).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                UserRoleType.toType(user.getRole()).name(),
                user.getId()).substring(7);

        return new LoginResponse(token);
    }
}