package com.example.outsourcingprojects.domain.task.service;


import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.domain.task.dto.*;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.outsourcingprojects.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    //사용하지 않는 어노테이션은 제거해주세요. ok
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 2. 전체 작업(목록) 조회
    @Transactional(readOnly = true)
    public PageDataDTO<TaskDTO> getAllTasks(String status, String query, Long assigneeId, Pageable pageable) {

        Long statusNum = status != null ? TaskStatusType.valueOf(status).getStatusNum() : null;

        // 목록조회
        Page<Task> taskPage = taskRepository.getAllTaskWithCondition(statusNum, query, assigneeId, pageable);
        Page<TaskDTO> responseDtoPage = taskPage.map(TaskDTO::from);

        return PageDataDTO.of(responseDtoPage);
    }

    // 3. 작업 상세 조회
    @Transactional(readOnly = true)
    public TaskDTO getTask(Long taskId) {

        Task task = taskRepository.findByIdAndDeletedAtIsNull(taskId)
                .orElseThrow(() -> new CustomException(TASK_NOT_FOUND));

        return TaskDTO.from(task);
    }

    @Transactional
    public CreateTaskResponse createTask(CreateTaskRequest request, Long userId) {
        User assignee;
        if (request.getAssigneeId() == null) {
            assignee = userRepository.findById(userId).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND));
        } else {
            assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND));
        }

        LocalDateTime dueDate = request.getDueDate() == null ? LocalDateTime.now().plusDays(7L) : request.getDueDate();


        if (assignee.getDeletedAt() != null) {
            throw new CustomException(USER_NOT_FOUND);
        }

        PriorityType priorityType = PriorityType.valueOf(request.getPriority());
        TaskStatusType statusType = TaskStatusType.toType(10L);
        // Task 인스턴스 생성
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                priorityType.getPriorityNum(),
                statusType.getStatusNum(),
                assignee,
                dueDate
        );

        // DB 저장
        Task savedTask = taskRepository.save(task);

        // Entity -> Response DTO 변환 후 반환
        return CreateTaskResponse.from(savedTask);
    }

    // 4. 작업 수정
    @Transactional
    public UpdateTaskResponse updateTask(Long taskId, UpdateTaskRequest requestDto, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(TASK_NOT_FOUND));

        if (task.getDeletedAt() != null) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        // 수정
        if (!task.getAssignee().getId().equals(userId)) {
            throw new CustomException(NO_UPDATE_PERMISSION);
        }

        // 타입 생성
        PriorityType priorityType = PriorityType.valueOf(requestDto.getPriority());
        TaskStatusType statusType = TaskStatusType.valueOf(requestDto.getStatus());
        task.update(
                requestDto.getTitle(),
                requestDto.getDescription(),
                priorityType.getPriorityNum(),
                statusType.getStatusNum(),
                requestDto.getDueDate()
        );


        return UpdateTaskResponse.from(task);


    }


    // 5. 작업 삭제
    @Transactional
    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(TASK_NOT_FOUND));

        if (task.getDeletedAt() != null) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        if (!task.getAssignee().getId().equals(userId)) {
            throw new CustomException(NO_DELETE_PERMISSION);
        }
        taskRepository.softDelete(taskId, LocalDateTime.now());
    }

    // 6. 작업 상태 변경
    @Transactional
    public StatusUpdateResponse statusUpdateTask(Long id, StatusUpdateRequest requestDto) {
        // 작업 조회
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomException(TASK_NOT_FOUND));

        if (task.getDeletedAt() != null) {
            throw new CustomException(TASK_NOT_FOUND);
        }

        TaskStatusType newStatus;
        try {
            newStatus = TaskStatusType.valueOf(requestDto.getStatus());
        } catch (CustomException e) {
            throw new CustomException(INVALID_STATUS_VALUE);
        }

        // 상태 변경
        task.updateStatus(newStatus.getStatusNum());

        return StatusUpdateResponse.from(task);
    }

}










