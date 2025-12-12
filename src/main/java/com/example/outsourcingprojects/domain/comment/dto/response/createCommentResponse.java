package com.example.outsourcingprojects.domain.comment.dto.response;

import com.example.outsourcingprojects.domain.comment.dto.UserInfoDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class createCommentResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final UserInfoDto user;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


}
