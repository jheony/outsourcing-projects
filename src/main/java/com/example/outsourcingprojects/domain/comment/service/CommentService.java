package com.example.outsourcingprojects.domain.comment.service;

import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.comment.dto.response.CommentListResponse;
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
import org.springframework.security.core.parameters.P;
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
    private final TaskRepository TaskRepository;

    //댓글 생성
    @Transactional
    public CreateCommentResponse create(Long taskId, Long userId, createCommentRequest commentRequest) {

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

        return CreateCommentResponse.from(savedComment);
    }


    //댓글 조회
    public CommentListResponse getComment(Long taskId, int page, int size, String sort) {

        Sort parentSort = sort.equals("oldest")
                ? Sort.by(Sort.Direction.ASC, "createdAt")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        Pageable pageable = PageRequest.of(page, size, parentSort);

        Page<Comment> parentsPage = commentRepository.findAllByTaskIdAndCommentIsNull(taskId, pageable);

        List<Comment> parents = parentsPage.getContent();

        List<Long> parentIds = parents.stream()
                .map(Comment::getId)
                .toList();

        List<Comment> children = parentIds.isEmpty()
                ? List.of()
                : commentRepository.findAllByTaskIdAndComment_IdIn(
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

//    public void update(Long taskId, Long  commentId, Long UpdateRequest request) {
//
//        //엽력된 작업ID가 데이터 베이스에 있는 작업ID와 동일한지 확인한다.
//         if(commentRepository.findByIdAndDeletedAtIsNull(taskId)) {
//             throw new IllegalArgumentException("타켓I가 없습니다.")
//         });
//        if(commentRepository.findByIdAndDeletedAtIsNull(taskId).equals(taskId)) {}
//
//        //입력된 댓글ID와 데이터베이스에 있는 본인댓글ID기 동일한지 확인한다.
//
//        //위 두 조건에 통과 하면 엔터티에 입력받은 댓글 내용을 업데이트 한다.
//    }

    //댓글 조회

    //댓글 수정

    //댓글 삭제

}