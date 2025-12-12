package com.example.outsourcingprojects.domain.comment.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class createCommentRequest {

    private final String content;
    private final Long parentId;
}