package com.example.outsourcingprojects.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class createCommentRequest {

    @NotNull
    private final String content;
    private final Long parentId;
}