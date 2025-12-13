package com.example.outsourcingprojects.domain.task.service;


import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.domain.task.dto.*;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.task.repository.TempTaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.outsourcingprojects.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TempTaskService {

    private final TaskRepository taskRepository;
    private final TempTaskRepository tempTaskRepository;
    private final UserRepository userRepository;

    // 1. 작업 생성
    @Transactional
    public CreateTaskResponseDto createTask(CreateTaskRequestDto request, Long userId) {
        User assignee;
        if (request.getAssigneeId().equals(null)) {
            assignee = userRepository.findById(userId).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND));
        } else {
            assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(
                    () -> new CustomException(USER_NOT_FOUND));
        }

        // 담당자 조회
        // TODO 이넘 타입 요청 및 응답 객체 수정. / deletedat 안나오게.

        PriorityType priorityType = PriorityType.valueOf(request.getPriority());
        TaskStatusType statusType = TaskStatusType.toType(10L);
        // Task 인스턴스 생성
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                priorityType.getPriorityNum(),
                statusType.getStatusNum(),
                assignee,
                request.getDueDate()
        );

        // DB 저장
        Task savedTask = taskRepository.save(task);

        // Entity -> Response DTO 변환 후 반환
        return CreateTaskResponseDto.from(savedTask);
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
        tempTaskRepository.softDelete(taskId, LocalDateTime.now());
    }

    // 6. 작업 상태 변경
    @Transactional
    public StatusUpdateResponseDto statusUpdateTask(Long id, StatusUpdateRequestDto requestDto) {
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

        return StatusUpdateResponseDto.from(task);
    }
}
