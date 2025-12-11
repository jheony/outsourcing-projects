package com.example.outsourcingprojects.domain.user.service;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.util.PasswordEncoder;
import com.example.outsourcingprojects.domain.user.dto.request.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.request.UpdateRequest;
import com.example.outsourcingprojects.domain.user.dto.response.*;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.outsourcingprojects.common.entity.QUser.user;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        if (request.getEmail().equals(userRepository.findByEmail(request.getEmail()))) {
            throw new CustomException(ErrorCode.ALREADY_TEAM_MEMBER);
        }
        if (request.getName().equals(userRepository.findByName((request.getName())))) {
            throw new IllegalArgumentException("이미 존재하는 사용자명 입니다.");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                10L
        );

        User savedUser = userRepository.save(user);

        return SignUpResponse.from(savedUser);
    }

    // 사용자 정보 조회
    @Transactional
    public UserInfoResponse info(Long id, Long userId) {
        if(userRepository.findByIdAndDeletedAtIsNull(userId).equals(userId)){
            throw new IllegalArgumentException("인증이 필요합니다.");
        }

        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserInfoResponse.from(user);
    }

    // 사용자 목록 조회
    @Transactional
    public UserListResponse usersInfo(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        List<User> users = userRepository.findAllByDeletedAtIsNull();

        List<UserSummaryResponse> summaries = users
                .stream()
                .map(UserSummaryResponse::from)
                .toList();

        return UserListResponse.from(summaries);
    }

    // 사용자 정보 수정
    @Transactional
    public UpdateResponse update(Long userId, Long targetId, UpdateRequest request) {

        if(userRepository.findByIdAndDeletedAtIsNull(userId).equals(userId)){
            throw new IllegalArgumentException("인증이 필요합니다.");
        }

        User targetUser = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저 입니다."));

        if (targetUser.getName().equals(request.getName())) {
            throw new IllegalArgumentException("기존의 " + request.getName() + " 과 동일합니다.");
        }
        if (targetUser.getEmail().equals(request.getEmail())) {
            throw new IllegalArgumentException("기존의 " + request.getEmail() + " 과 동일합니다.");
        }
        if (passwordEncoder.matches(request.getPassword(), targetUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 변경되지 않았습니다.");
        }

        targetUser.update(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
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
    public AbleUsersListResponse findAddableUsers(Long teamId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        List<User> users;
        if(teamId != null) {
            users = userRepository.findUsersNotInTeam(teamId);
        } else {
            users = userRepository.findAllByDeletedAtIsNull();
        }

        List<AbleUserSummaryResponse> ableUserUsers = users.stream()
                .map(AbleUserSummaryResponse::from)
                .toList();

        return AbleUsersListResponse.from(ableUserUsers);
    }

}
