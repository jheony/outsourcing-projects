package com.example.outsourcingprojects.domain.commentServiceTest.service;

import com.example.outsourcingprojects.common.entity.Comment;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.comment.dto.request.createCommentRequest;
import com.example.outsourcingprojects.domain.comment.dto.response.CreateCommentResponse;
import com.example.outsourcingprojects.domain.comment.repository.CommentRepository;
import com.example.outsourcingprojects.domain.comment.service.CommentService;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성 성공 - parentId 없음")
    void create_success_parentNull() {
        // Given
        Long taskId = 1L;
        Long userId = 2L;

        User user = mock(User.class);   // 엔티티도 그냥 mock
        Task task = mock(Task.class);

        createCommentRequest req = mock(createCommentRequest.class);
        when(req.getParentId()).thenReturn(null);
        when(req.getContent()).thenReturn("내용");

        when(userRepository.findByIdAndDeletedAtIsNull(userId))
                .thenReturn(Optional.of(user));
        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        Comment saved = new Comment(user, task, null, "내용");
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(saved);

        // When
        CreateCommentResponse res = commentService.create(taskId, userId, req);

        // Then
        assertEquals("내용", res.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}
