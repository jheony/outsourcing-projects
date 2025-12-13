package com.example.outsourcingprojects.domain.user.service;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.response.VerifyPasswordResponse;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.request.UpdateUserRequest;
import com.example.outsourcingprojects.domain.user.dto.response.*;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.USER_NAME_ALREADY_EXISTS);
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                20L
        );

        User savedUser = userRepository.save(user);

        return SignUpResponse.from(savedUser);
    }

    // 사용자 정보 조회
    @Transactional
    public UserInfoResponse info(Long targetId, Long userId) {

        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        if (!userId.equals(target.getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        return UserInfoResponse.from(target);
    }

    // 사용자 목록 조회
    @Transactional
    public List<UserSummaryResponse> usersInfo(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        List<User> users = userRepository.findAllByDeletedAtIsNull();

        return users
                .stream()
                .map(UserSummaryResponse::from)
                .toList();
    }

    // 사용자 정보 수정
    @Transactional
    public UpdateResponse update(Long loginUserId, Long targetId, UpdateUserRequest request) {

        if (!loginUserId.equals(targetId)) {
            throw new CustomException(ErrorCode.ALREADY_TEAM_MEMBER);
        }

        User targetUser = userRepository.findByIdAndDeletedAtIsNull(targetId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저 입니다."));

        if (request.getName() != null && request.getName().equals(targetUser.getName())) {
            throw new IllegalArgumentException("기존 이름과 동일합니다.");
        }
        if (request.getEmail() != null && request.getEmail().equals(targetUser.getEmail())) {
            throw new IllegalArgumentException("기존 이메일과 동일합니다.");
        }
        if (request.getPassword() != null && passwordEncoder.matches(targetUser.getPassword(), request.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 동일합니다.");
        }

        targetUser.update(
                request.getName(),
                request.getEmail()
        );

        return UpdateResponse.from(targetUser);
    }

    // 회원 탈퇴
    @Transactional
    public void softDelete(Long targetId, Long sessionUserId) {

        if (!sessionUserId.equals(userRepository.findById(targetId).get().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        User user = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저 입니다."));

        if (user.getDeletedAt() != null) {
            throw new IllegalArgumentException("이미 탈퇴 처리된 유저입니다.");
        }

        user.delete();
    }

    // 추가 가능한 사용자 조회
    @Transactional
    public List<AbleUserSummaryResponse> findAddableUsers(Long teamId, Long userId) {

        User user =userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<User> users;
        if (user != null) {
            users = userRepository.findUsersNotInTeam(teamId);
        } else {
            users = userRepository.findAllByDeletedAtIsNull();
        }

        List<AbleUserSummaryResponse> ableUsers = users
                .stream()
                .map(AbleUserSummaryResponse::from)
                .toList();

        return ableUsers;
    }


    //비밀번호 확인
    @Transactional
    public VerifyPasswordResponse verifyPassword(Long userId, String inputPassword) {

        // 1. 유저 조회
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 2. 입력 비밀번호 vs 저장된 비밀번호 비교
        boolean match = passwordEncoder.matches(inputPassword, user.getPassword());

        // 3. 결과 반환
        return new VerifyPasswordResponse(match);
    }

}
