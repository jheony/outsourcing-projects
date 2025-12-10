package com.example.outsourcingprojects.domain.user.service;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.request.UpdateRequest;
import com.example.outsourcingprojects.domain.user.dto.response.*;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.outsourcingprojects.common.model.UserRoleType.USER;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        if(request.getEmail().equals(userRepository.findByEmail(request.getEmail()))) {
            throw new IllegalArgumentException("이미 존재하는 미에밀 입니다.");
        }
        if(request.getName().equals(userRepository.findByName((request.getName())))) {
            throw new IllegalArgumentException("이미 존재하는 사용자명 입니다.");
        }

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
    @Transactional
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
    @Transactional
    public UserListResponse usersInfo() {

        List<UserSummaryResponse> userSummaryResponseList = userRepository.findAll()
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

    @Transactional
    public UpdateResponse update(Long id, UpdateRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저 입니다."));

        //이름
        if (user.getName().equals(request.getName())) {
            throw new IllegalArgumentException("기존의 " + request.getName() +" 과 동일합니다.");
        }//이메일
        if (user.getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("기존의 " + request.getEmail() +" 과 동일합니다.");
        }//비번
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 변경되지 않았습니다.");
        }
        log.info("aafdsfdsf");
        //변경되면 엔터티에 업데이트
        user.update(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
                );

        return UpdateResponse.from(user);
    }

    // 사용자 정보 수정

    // 회원 탈퇴(영구 삭제)

    // 추가 가능한 사용자 조회
}
