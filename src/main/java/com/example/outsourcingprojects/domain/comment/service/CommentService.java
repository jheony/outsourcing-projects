package com.example.outsourcingprojects.domain.comment.service;

import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.domain.comment.dto.request.UpdateCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.CommentListResponse;
import com.example.outsourcingprojects.domain.comment.dto.response.UpdateCommentResponse;
import com.example.outsourcingprojects.domain.comment.dto.userDto.UserDto;
import com.example.outsourcingprojects.domain.comment.dto.response.GetCommentResponse;
import com.example.outsourcingprojects.domain.comment.dto.request.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.CreateCommentResponse;
import com.example.outsourcingprojects.domain.comment.repository.CommentRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    //댓글 생성
    @Transactional
    public CreateCommentResponse create(Long taskId, Long userId, createCommentRequest commentRequest) {

        User userinfo = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_REQUIRED));

        Task taskInfo = taskRepository.findById((taskId))
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        Comment parentId = null;
        if (commentRequest.getParentId() != null) {
            parentId = commentRepository.findByIdAndDeletedAtIsNull(commentRequest.getParentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.TASK_OR_PARENT_COMMENT_NOT_FOUND));
        }

        UserDto.from(userinfo);
        Comment comment = new Comment(userinfo, taskInfo, parentId, commentRequest.getContent());
        Comment savedComment = commentRepository.save(comment);

        return CreateCommentResponse.from(savedComment);
    }


    //댓글 조회
    @Transactional
    public CommentListResponse getComment(Long taskId, int page, int size, String sort) {

        taskRepository.findByIdAndDeletedAtIsNull(taskId)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        Sort parentSort = sort.equals("oldest")
                ? Sort.by(Sort.Direction.ASC, "createdAt")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        Pageable pageable = PageRequest.of(page, size, parentSort);
        Page<Comment> parentsPage = commentRepository.findAllByTaskIdAndCommentIsNullAndDeletedAtIsNull(taskId, pageable);
        List<Comment> parents = parentsPage.getContent();

        List<Long> parentIds = parents.stream()
                .map(Comment::getId)
                .toList();

        List<Comment> children = parentIds.isEmpty()
                ? List.of()
                : commentRepository.findAllByTaskIdAndComment_IdInAndDeletedAtIsNull(
                taskId,
                parentIds,
                Sort.by(Sort.Direction.ASC, "createdAt")
        );

        Map<Long, List<Comment>> childrenMap =
                children.stream()
                        .collect(Collectors.groupingBy(
                                child -> child.getComment().getId(),
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        List<Comment> ordered = new ArrayList<>();
        for (Comment parent : parents) {
            ordered.add(parent);
            ordered.addAll(childrenMap.getOrDefault(parent.getId(), List.of()));
        }

        List<GetCommentResponse> result = ordered.stream()
                .map(GetCommentResponse::from)
                .toList();

        return CommentListResponse.from(result, parentsPage);
    }


    //댓글 수정
    @Transactional
    public UpdateCommentResponse update(Long taskId, Long commentId, UpdateCommentRequest request) {

        taskRepository.findByIdAndDeletedAtIsNull(taskId)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        Comment findComment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!findComment.getTask().getId().equals(taskId)) {
            throw new CustomException(ErrorCode.NO_COMMENT_UPDATE_PERMISSION);
        }

        findComment.update(request.getContent());
        return UpdateCommentResponse.from(findComment);
    }


    //댓글 삭제
    @Transactional
    public void softDelete(Long userId, Long taskId, Long commentId) {

        userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NO_COMMENT_DELETE_PERMISSION));

        taskRepository.findByIdAndDeletedAtIsNull(taskId)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        Comment findComment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

//        if (findComment.getDeletedAt() != null) {
//            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
//        }

        findComment.delete();
    }


}