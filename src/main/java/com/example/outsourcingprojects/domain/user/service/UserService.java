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

import static com.example.outsourcingprojects.common.exception.ErrorCode.NO_READ_PERMISSION;
import static com.example.outsourcingprojects.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_USERNAME);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EMAIL);
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

        User target = userRepository.findByIdAndDeletedAtIsNull(targetId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User requester = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        //수정 가능 조건(관리자 미허용)
        if (!userId.equals(target.getId())) {
            throw new CustomException(NO_READ_PERMISSION);
        }

//        //수정 가능 조건(관리자 허용)
//        boolean isAdmin = requester.getRole() == 10L;
//        if (!requester.getId().equals(target.getId()) && !isAdmin) {
//            throw new CustomException(ErrorCode.NO_READ_PERMISSION);
//        }

        return UserInfoResponse.from(target);
    }

    // 사용자 목록 조회
    @Transactional
    public List<UserSummaryResponse> usersInfo(Long userId) {

        userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

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
            throw new CustomException(NO_READ_PERMISSION);
        }

        User targetUser = userRepository.findByIdAndDeletedAtIsNull(targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        if (request.getName() != null && request.getName().equals(targetUser.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NAME);
        }
        if (request.getEmail() != null && request.getEmail().equals(targetUser.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_EMAIL);
        }
        if (request.getPassword() != null && passwordEncoder.matches(targetUser.getPassword(), request.getPassword())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_PASSWORD);
        }

        targetUser.update(
                request.getName(),
                request.getEmail()
        );

        return UpdateResponse.from(targetUser);
    }


    // 회원 탈퇴
    @Transactional
    public void softDelete(Long targetId, Long loginUserId) {

        if (!loginUserId.equals(targetId)) {
            throw new CustomException(ErrorCode.NO_READ_PERMISSION);
        }

        User me = userRepository.findByIdAndDeletedAtIsNull(targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        if (me.getDeletedAt() != null) {
            throw new CustomException(ErrorCode.ALREADY_DELETED_USER);
        }

        me.delete();
    }


    // 추가 가능한 사용자 조회
    @Transactional
    public List<AbleUserSummaryResponse> findAddableUsers(Long teamId, Long userId) {

        userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<User> users;
        if (teamId != null) {
            users = userRepository.findUsersNotInTeam(teamId);
        } else {
            users = userRepository.findAllByDeletedAtIsNull();
        }

        return users.stream()
                .map(AbleUserSummaryResponse::from)
                .toList();
    }


    //비밀번호 확인
    @Transactional
    public VerifyPasswordResponse verifyPassword(Long userId, String inputPassword) {

        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean match = passwordEncoder.matches(inputPassword, user.getPassword());

        return new VerifyPasswordResponse(match);
    }

}
