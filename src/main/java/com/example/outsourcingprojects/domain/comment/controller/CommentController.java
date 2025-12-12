package com.example.outsourcingprojects.domain.comment.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.comment.dto.request.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.createCommentResponse;
import com.example.outsourcingprojects.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{taskId}/comments")
    public GlobalResponse<createCommentResponse> createCommentHandler(
            HttpServletRequest userToken,
            @PathVariable Long taskId,
            @RequestBody createCommentRequest request
    ) {
        Long userId = (Long) userToken.getAttribute("userId");
        createCommentResponse createComment = commentService.createComment(taskId, userId, request);
        return GlobalResponse.success("댓글이 작성되었습니다.", createComment);
    }


    //댓글 조회

    //댓글 수정

    //댓글 삭제

}
