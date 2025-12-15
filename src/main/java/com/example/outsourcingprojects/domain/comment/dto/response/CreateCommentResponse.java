package com.example.outsourcingprojects.domain.comment.dto.response;

import com.example.outsourcingprojects.domain.entity.Comment;
import com.example.outsourcingprojects.domain.comment.dto.userDto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final UserDto user;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static CreateCommentResponse from(Comment comment) {
        return new CreateCommentResponse(
                comment.getId(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                UserDto.from(comment.getUser()),
                comment.getContent(),
                comment.getComment() != null ? comment.getComment().getId() : null,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

}
