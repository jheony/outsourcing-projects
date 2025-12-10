package com.example.outsourcingprojects.domain.task.service;

import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 1. 작업 생성
    @Transactional
    public CreateTaskResponseDto createTask(CreateTaskRequestDto request) {

        // 담당자 조회
        User assignee = userRepository.findById(request.getAssignedId())
                .orElseThrow(() -> {
                    log.error("담당자를 찾을 수 없습니다." + request.getAssignedId());
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
        return Response(savedTask);
    }

    private CreateTaskResponseDto Response(Task task) {
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

    // 2. 전체 작업(목록) 조회
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CreateTaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        List<CreateTaskResponseDto> responseDto = new ArrayList<>();

        for (Task task : tasks) {
            CreateTaskResponseDto dto = new CreateTaskResponseDto(
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
            responseDto.add(dto);

        }
        return responseDto;
    }

    // 3. 작업 상세 조회
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public CreateTaskResponseDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("작업을 찾을 수 없습니다"));

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

    // 4. 작업 수정
    @Transactional
    public CreateTaskResponseDto updateTask(Long taskId, CreateTaskRequestDto requestDto, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("작업을 찾을 수 없습니다"));

        if (!task.getId().equals(userId)) {
            throw new IllegalArgumentException("작업을 수정할 수 없습니다");
        }

        task.update(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getStatus(),
                requestDto.getDueDate()
        );
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


//    // 5. 작업 삭제
//    Transactional
//    public void deleTask(Long taskId, Long userId) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new IllegalArgumentException("작업을 찾을 수 없습니다"));
//
//        if (!task.getId().equals(userId))
//    }
}








