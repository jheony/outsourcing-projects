package com.example.outsourcingprojects.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCommentRequest {

    @NotBlank(message = "댓슬 내용은 필수 입니다.")
    @Size(max = 500, message =  "내용은 500자 이내로 입력해주세요")
    private final String content;

}
