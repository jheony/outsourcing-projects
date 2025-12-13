package com.example.outsourcingprojects.domain.comment.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.comment.dto.request.UpdateCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.request.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.CommentListResponse;
import com.example.outsourcingprojects.domain.comment.dto.response.CreateCommentResponse;
import com.example.outsourcingprojects.domain.comment.dto.response.UpdateCommentResponse;
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
    public GlobalResponse<CreateCommentResponse> createCommentHandler(
            HttpServletRequest userToken,
            @PathVariable Long taskId,
            @RequestBody createCommentRequest request
    ) {
        Long userId = (Long) userToken.getAttribute("userId");
        CreateCommentResponse createComment = commentService.create(taskId, userId, request);
        return GlobalResponse.success("댓글이 작성되었습니다.", createComment);
    }

    //댓글 조회
    @GetMapping("/{taskId}/comments")
    public GlobalResponse<CommentListResponse> getCommentHandler(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        CommentListResponse response = commentService.getComment(taskId, page, size, sort);
        return GlobalResponse.success("댓글 목록을 조회했습니다.", response);
    }

    //댓글 수정
    @PutMapping("/{taskId}/comments/{commentId}")
    public GlobalResponse<UpdateCommentResponse> updateHandler(@PathVariable Long taskId,
                                                               @PathVariable Long commentId,
                              @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse response = commentService.update(taskId, commentId, request);
        return GlobalResponse.success("댓글이 수정되었습니다.", response);
    }

    //댓글 삭제

}
