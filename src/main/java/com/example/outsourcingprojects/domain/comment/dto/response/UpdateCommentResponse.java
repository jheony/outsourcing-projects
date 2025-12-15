package com.example.outsourcingprojects.domain.comment.dto.response;

import com.example.outsourcingprojects.domain.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateCommentResponse {

    private final Long id;
    private final Long taskId;
    private final Long userId;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UpdateCommentResponse from(Comment comment) {
        return new UpdateCommentResponse(
                comment.getId(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                comment.getContent(),
                comment.getComment() != null ? comment.getComment().getId() : null,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
