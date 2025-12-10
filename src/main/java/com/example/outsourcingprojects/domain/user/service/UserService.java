package com.example.outsourcingprojects.domain.user.service;


import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.*;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.outsourcingprojects.common.model.UserRoleType.USER;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                USER.getRoleNum()
        );

        User savedUser = userRepository.save(user);
        SignUpResponse userResponse = new SignUpResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );

        return userResponse;
    }

    // 사용자 정보 조회
    public UserInfoResponse info(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 아이디가 없습니다."));
        UserInfoResponse userInfo = new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
                );

        return userInfo;
    }

    // 사용자 목록 조회
    public UserListResponse usersInfo() {

        List<UserSummaryResponse> userSummaryResponseList =  userRepository.findAll()
                .stream()
                .map(user -> new UserSummaryResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getName(),
                        user.getRole(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
        UserListResponse userListResponse = new UserListResponse(userSummaryResponseList);

        return userListResponse;
    }

    // 사용자 정보 수정

    // 회원 탈퇴(영구 삭제)

    // 추가 가능한 사용자 조회
}
