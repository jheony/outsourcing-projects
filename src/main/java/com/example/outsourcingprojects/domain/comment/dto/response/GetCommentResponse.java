package com.example.outsourcingprojects.domain.comment.dto.response;

import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.domain.comment.dto.userDto.GetUserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCommentResponse {

    private final Long id;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final GetUserDto user;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static GetCommentResponse from(Comment comment) {
        return new GetCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                GetUserDto.from(comment.getUser()),
                comment.getComment() != null ? comment.getComment().getId() : null,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
