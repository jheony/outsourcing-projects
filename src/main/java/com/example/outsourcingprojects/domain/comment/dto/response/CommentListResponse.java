package com.example.outsourcingprojects.domain.comment.dto.response;

import com.example.outsourcingprojects.common.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentListResponse {

    private final List<GetCommentResponse> content;
    private final long totalElements;
    private final int totalPages;
    private final int size;
    private final int number;

    public static CommentListResponse from(List<GetCommentResponse> comments, Page<Comment> page) {

        return new CommentListResponse(
                comments,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber()
        );
    }
}
