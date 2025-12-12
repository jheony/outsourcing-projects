package com.example.outsourcingprojects.domain.comment.controller;

import com.example.outsourcingprojects.common.entity.User;

import java.time.LocalDateTime;

public class createCommentResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final UserInfoDto user;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public createCommentResponse(Long id, Long taskId, Long userId, UserInfoDto user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
