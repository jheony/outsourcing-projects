package com.example.outsourcingprojects.domain.task.service;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.model.response.ErrorResponse;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequest;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponse;
import com.example.outsourcingprojects.domain.task.dto.TaskDTO;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.outsourcingprojects.common.exception.ErrorCode.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    @DisplayName("작업 생성 성공")
    void createTask_success() {
        //given < 테스트를 하는 데 필요한 데이터
        Long userId = 1L;
        CreateTaskRequest request = new CreateTaskRequest(1L, "title", "description", "HIGH", LocalDateTime.now());

        User assignee = new User("username", "email", "password", "name", 10L);
        Task task = new Task("title", "description", 30L, 10L, assignee, request.getDueDate());

        when(userRepository.findByIdAndDeletedAtIsNull(userId)).thenReturn(Optional.of(assignee));
        when(taskRepository.save(any())).thenReturn(task);

        //when < 테스트의 실제 실행

        CreateTaskResponse result = taskService.createTask(request, userId);
        //then < 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("title");

    }

    @Test
    @DisplayName("작업생성 담당자 ID Null이고 인증도 안될때")
    void createTask_Fail_AssignIdNull() {
        //given
        Long userId = 1L;
        CreateTaskRequest request = new CreateTaskRequest(null, "title", "description", "HIGH", LocalDateTime.now());

        when(userRepository.findByIdAndDeletedAtIsNull(userId)).thenReturn(Optional.empty());
        //when & then

        assertThatThrownBy(() -> taskService.createTask(request, userId)).isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", USER_NOT_FOUND);
    }

    @Test
    @DisplayName("모든 작업 조회_성공")
    void getAllTasks_success() {
        //given
        String status = "IN_PROGRESS";
        String query = "test";
        Long assigneeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        User user1 = new User("username1", "email", "password", "name", 10L);
        User user2 = new User("username2", "email", "password", "name", 10L);

        Task task1 = new Task("title1", "description", 10L, 20L, user1, LocalDateTime.now());
        Task task2 = new Task("title2", "description", 10L, 20L, user2, LocalDateTime.now());

        Page<Task> taskPage = new PageImpl<>(List.of(task1,task2),pageable,2);

        when(taskRepository.getAllTaskWithCondition(20L,query,assigneeId,pageable)).thenReturn(taskPage);
        //when
        PageDataDTO<TaskDTO> result = taskService.getAllTasks("IN_PROGRESS","test",1L,PageRequest.of(0, 10));

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0)).isInstanceOf(TaskDTO.class);


    }
}
