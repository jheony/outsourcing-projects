package com.example.outsourcingprojects.domain.task.service;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 1. 작업 생성
    public CreateTaskResponseDto createTask(CreateTaskRequestDto request) {

        // 담당자 조회
        User assignee = userRepository.findById(request.getAssignedId())
                .orElseThrow(() -> {
                    log.error("담당자를 찾을 수 없습니다. 요청된 ID: " + request.getAssignedId());
                    throw new IllegalArgumentException("담당자를 찾을 수 없습니다.");
                });

        // Task 인스턴스 생성
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                request.getStatus(),
                assignee,
                request.getDueDate()
        );

        // DB 저장
        Task savedTask = taskRepository.save(task);

        // Entity -> Response DTO 변환 후 반환
        return toResponse(savedTask);
        }
        private CreateTaskResponseDto toResponse(Task task) {
            return new CreateTaskResponseDto(
                    task.getId(),
                    task.getAssignee().getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority(),
                    task.getStatus(),
                    task.getDueDate(),
                    task.getCreatedAt(),
                    task.getUpdatedAt(),
                    task.getDeletedAt()
            );
        }
    }


