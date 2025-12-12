package com.example.outsourcingprojects.domain.comment.service;

import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.comment.dto.UserDto;
import com.example.outsourcingprojects.domain.comment.dto.request.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.createCommentResponse;
import com.example.outsourcingprojects.domain.comment.repository.CommentRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository TaskRepository;

    //댓글 생성
    @Transactional
    public createCommentResponse createComment(Long taskId, Long userId, createCommentRequest commentRequest) {

        User userinfo = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        Task taskInfo = TaskRepository.findById((taskId))
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Comment parentId = null;
        if (commentRequest.getParentId() != null) {
            parentId = commentRepository.findByIdAndDeletedAtIsNull(commentRequest.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글 없음"));
        }

        UserDto.from(userinfo);
        Comment comment = new Comment(userinfo, taskInfo, parentId, commentRequest.getContent());
        Comment savedComment = commentRepository.save(comment);

        return createCommentResponse.from(savedComment);
    }

    //댓글 조회

    //댓글 수정

    //댓글 삭제

}