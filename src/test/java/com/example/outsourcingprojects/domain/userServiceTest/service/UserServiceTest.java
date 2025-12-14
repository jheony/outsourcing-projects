package com.example.outsourcingprojects.domain.userServiceTest.service;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.response.SignUpResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import com.example.outsourcingprojects.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 - 중복 없고 비번 인코딩 정상, 저장 성공")
    void signUpUser_success() {
        //given
        SignUpRequest request = new SignUpRequest("하륜짱짱맨", "test@test.com", "Hkkk@123456", "하륜");

        when(userRepository.existsByUsername("하륜짱짱맨")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(passwordEncoder.encode("Hkkk@123456")).thenReturn("ENCODED");

        User savedUser = new User("하륜짱짱맨", "test@test.com", "ENCODED", "하륜", 20L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);


        //when
        SignUpResponse response = userService.signUpUser(request);

        //then
        assertEquals("하륜짱짱맨", response.getUsername());
        assertEquals("test@test.com", response.getEmail());
        verify(userRepository, times(1)).save(any(User.class));

    }
}
