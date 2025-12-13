package com.example.outsourcingprojects.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentRequest {

    @NotNull
    private final String content;

}
