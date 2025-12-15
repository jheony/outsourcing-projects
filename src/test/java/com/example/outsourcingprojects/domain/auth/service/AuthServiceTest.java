package com.example.outsourcingprojects.domain.auth.service;

import com.example.outsourcingprojects.domain.entity.User;
import com.example.outsourcingprojects.common.util.JwtUtil;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.auth.dto.request.LoginRequest;
import com.example.outsourcingprojects.domain.auth.dto.response.LoginResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("로그인 성공")
    void loginTest_Success() {

        // given
        LoginRequest testRequest = new LoginRequest();

        ReflectionTestUtils.setField(testRequest, "username", "username");
        ReflectionTestUtils.setField(testRequest, "password", "password");

        User user = new User("username", "email", "password", "name", 10L);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(userRepository.findByUsernameAndDeletedAtIsNull(anyString())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        when(jwtUtil.generateToken(anyString(), anyString(), anyLong())).thenReturn("Bearer jwtjwtjwtjwtjwt");

        // when
        LoginResponse response = authService.login(testRequest);

        // then
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("로그인 시 유저 찾기 실패")
    void loginTest_UserNotFound_Fail() {

        // given
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "username", "username");
        ReflectionTestUtils.setField(request, "password", "password");

        when(userRepository.findByUsernameAndDeletedAtIsNull(anyString())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> authService.login(request)).hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("로그인 시 비밀번호 불일치")
    void loginTest_NO_PERMISSION_Fail() {

        // given
        LoginRequest testRequest = new LoginRequest();

        ReflectionTestUtils.setField(testRequest, "username", "username");
        ReflectionTestUtils.setField(testRequest, "password", "password");

        User user = new User("username", "test@test.com", "password", "name", 10L);
        ReflectionTestUtils.setField(user, "id", 1L);

        when(userRepository.findByUsernameAndDeletedAtIsNull(anyString())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(testRequest)).hasMessage("비밀번호가 올바르지 않습니다.");
    }
}