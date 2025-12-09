package com.example.outsourcingprojects.domain.user.service;


import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.SignUpResponse;
import com.example.outsourcingprojects.domain.user.dto.UserInfoResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import static com.example.outsourcingprojects.common.model.UserRoleType.ADMIN;
import static com.example.outsourcingprojects.common.model.UserRoleType.USER;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        //엔터티 전달
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                20L
        );

        //데이터베이스 저장
        User savedUser = userRepository.save(user);

        //DTO 컨테스트 꺼내오기
        SignUpResponse userResponse = new SignUpResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getName(),
                USER.getRoleNum(),
                savedUser.getCreatedAt()
        );

        return userResponse;
    }

    // 사용자 정보 조회
    public UserInfoResponse info(Long userId) {
        //입력한 유저 아이디가 데이터베이스에 있는지 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 아이디가 없습니다."));

        //있다면 데이터베이스에 있는 유저 정보를 꺼내서 DOT에 담기
        UserInfoResponse userInfo= new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                USER.getRoleNum(),
                user.getCreatedAt(),
                user.getUpdatedAt()
                );

        //DTO에 담았다면 리턴하기
        return userInfo;
    }

    // 사용자 목록 조회

    // 사용자 정보 수정

    // 회원 탈퇴(영구 삭제)

    // 추가 가능한 사용자 조회
}
