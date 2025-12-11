package com.example.outsourcingprojects.domain.auth.service;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.UserRoleType;
import com.example.outsourcingprojects.common.util.JwtUtil;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.auth.dto.request.LoginRequest;
import com.example.outsourcingprojects.domain.auth.dto.request.VerifyPasswordRequest;
import com.example.outsourcingprojects.domain.auth.dto.response.LoginResponse;
import com.example.outsourcingprojects.domain.auth.dto.response.VerifyPasswordResponse;
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

    @Transactional
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                UserRoleType.USER.name(),
                user.getId()).substring(7);

        return new LoginResponse(token);
    }

    @Transactional
    public VerifyPasswordResponse verifyPasswordHandler(String username, VerifyPasswordRequest request) {
        //Handler라는 명칭은 Controller에서 사용하기로 약속하였습니다.
        //주석이 남아있습니다. 확인 해주세요.
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalStateException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
//            new VerifyPasswordResponse(false);
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        return new VerifyPasswordResponse(true);
    }
}
