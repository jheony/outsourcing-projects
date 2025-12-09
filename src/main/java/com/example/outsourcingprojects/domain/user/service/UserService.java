package com.example.outsourcingprojects.domain.user.service;


import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.user.dto.SignUpRequest;
import com.example.outsourcingprojects.domain.user.dto.SignUpResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);
    private final UserRepository userRepository;

    @Transactional
    public SignUpResponse signUpUser(SignUpRequest request) {

        //엔터티 값 넣기
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                20L
        );

        //데이터베이스 저장
        User savedUser = userRepository.save(user);

        //DTO 컨테스트 꺼내오기
        SignUpResponse userResponse= new SignUpResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getName(),
                10,
                savedUser.getCreatedAt()
        );

        return userResponse;
    }
}
