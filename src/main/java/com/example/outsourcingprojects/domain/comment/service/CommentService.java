package com.example.outsourcingprojects.domain.comment.service;

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
    private final TaskRepository TaskRepository;
    //댓글 생성
    @Transactional
    public createCommentResponse createComment(createCommentRequest taskId, Long userId) {


        Task findComment = commentRepository.findByIdAndDeletedAtIsNull((taskId.getParentId()))
                .orElseThrow(()->new IllegalArgumentException("에러에러")).getTask();

        //입력한 댓글내용 추출
        //엔터티에 넣기
    }


    //댓글 조회


    //댓글 수정

    //댓글 삭제


}
