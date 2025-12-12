package com.example.outsourcingprojects.domain.comment.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks/")
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @GetMapping("/{taskId}/comments")
    public GlobalResponse<createCommentResponse> createCommentHandler(
            @PathVariable createCommentRequest taskId, HttpServletRequest userToken) {

        Long userId = (Long) userToken.getAttribute("userId");
        commentService.createComment(taskId,userId);
    }


    //댓글 조회


    //댓글 수정

    //댓글 삭제


}
