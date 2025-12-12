package com.example.outsourcingprojects.domain.comment.service;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
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
import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.domain.comment.controller.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.repository.CommentRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
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
    public GlobalResponse<createCommentResponse> createComment(Long taskId, Long userId, createCommentRequest request) {

        //유저정보 가져오기
        User userinfo = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new IllegalArgumentException("에러에러"));
        //게시물PK 가져오기
        Task task = TaskRepository.findById((taskId))
                .orElseThrow(() -> new IllegalArgumentException("에러에러"));

        if (request.getParentId() != null) {

        }
    }


    //댓글 조회


    //댓글 수정

    //댓글 삭제
}