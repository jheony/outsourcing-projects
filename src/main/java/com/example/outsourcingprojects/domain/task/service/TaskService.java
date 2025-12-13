package com.example.outsourcingprojects.domain.task.service;


import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.domain.comment.repository.CommentRepository;
import com.example.outsourcingprojects.domain.task.dto.*;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    //사용하지 않는 어노테이션은 제거해주세요. ok
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
//
//    // 1. 작업 생성
//    @Transactional
//    public CreateTaskResponseDto createTask(CreateTaskRequestDto request) {
//
//        // 담당자 조회
//        User assignee = userRepository.findById(request.getAssigneeId())
//                .orElseThrow(() -> {
//                    log.error("담당자를 찾을 수 없습니다.assignId: {}", request.getAssigneeId());
//                    throw new CustomException(ErrorCode.ASSIGNEE_NOT_FOUND);
//                });
//        // TODO 이넘 타입 요청 및 응답 객체 수정. / deletedat 안나오게.
//
//        PriorityType priorityType = PriorityType.toType(request.getPriority());
//        TaskStatusType statusType = TaskStatusType.toType(10L); //status 디폴트 값 설정
//        // Task 인스턴스 생성
//        Task task = new Task(
//                request.getTitle(),
//                request.getDescription(),
//                priorityType.getPriorityNum(),
//                statusType.getStatusNum(),
//                assignee,
//                request.getDueDate()

    /// /        );
//
//        // DB 저장
//        Task savedTask = taskRepository.save(task);
//
//        // Entity -> Response DTO 변환 후 반환
//        return CreateTaskResponseDto.from(savedTask);
//    }
    private CreateTaskResponseDto response(Task task) throws Exception {
        //메서드 명은 소문자로 시작해야 합니다. ok
        // 이 메서드는 정적 팩토리 메서드의 역할을 하고 있는것 같은데
        // 정적 팩토리 메서드에 대해서 공부해보시고 CreateTaskResponseDto에서 작성하고 활용하시면 더 좋을 것 같습니다.
        return new CreateTaskResponseDto(
                task.getId(),
                task.getAssignee().getId(),
                task.getTitle(),
                task.getDescription(),
                PriorityType.toType(task.getPriority()).name(),
                TaskStatusType.toType(task.getStatus()).name(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()

        );
    }

    // 2. 전체 작업(목록) 조회
    @Transactional(readOnly = true)
    public PageDataDTO<TaskListResponseDto> getAllTasks(String status, String query, Long assigneeId, Pageable pageable) {

        Long statusNum = TaskStatusType.valueOf(status).getStatusNum();

        // 목록조회
        Page<Task> taskPage = taskRepository.getAllTaskWithCondition(statusNum, query, assigneeId, pageable);
        Page<TaskListResponseDto> responseDtoPage = taskPage.map(TaskListResponseDto::from);

        return PageDataDTO.of(responseDtoPage);
    }

    // 3. 작업 상세 조회
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        return TaskResponse.from(task);
    }

    // 4. 작업 수정
    @Transactional
    public UpdateTaskResponse updateTask(Long taskId, UpdateTaskRequest requestDto, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("작업을 찾을 수 없습니다. taskId: {}", taskId);
                    return new IllegalArgumentException("작업이 찾을 수 없습니다.");
                });

        // 수정
        if (!task.getAssignee().getId().equals(userId)) {
            log.error("수정 권한이 없습니다. taskId: {}, userId: {}, assigneeId: {}",
                    taskId, userId, task.getAssignee().getId());
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        // 타입 생성
        /// gsdklhgdsljgdl;kkdjg;sjkg;ls;ljgl;sdjgksjgjsgkj
        PriorityType priorityType = PriorityType.toType(requestDto.getPriority());
        TaskStatusType statusType = TaskStatusType.toType(requestDto.getStatus());
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
                .orElseThrow(() -> {
                    log.error("작업을 찾을 수 없습니다. taskId: {}", taskId);
                    return new IllegalArgumentException("작업을 찾을 수 없습니다.");
                });

        if (!task.getAssignee().getId().equals(userId)) {
            log.error("삭제 권한이 없습니다. taskId: {}, userId: {}, assigneeId: {}", taskId, userId, task.getAssignee().getId());
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        task.delete();
    }

//    // 6. 작업 상태 변경
//    @Transactional
//    public StatusUpdateResponseDto statusUpdateTask(Long id, StatusUpdateRequestDto requestDto) {
//        // 작업 조회
//        Task task = taskRepository.findById(id)
//                .orElseThrow(() -> {
//                    log.error("유효하지 않은 상태 값입니다. taskId: {}", id);
//                    return new IllegalArgumentException("유효하지 않은 상태 값입니다.");
//                });
//
//        TaskStatusType newStatus;
//        try {
//            newStatus = TaskStatusType.toType(requestDto.getStatus());
//        } catch (IllegalArgumentException e) {
//            log.error("유효하지 않은 상태 값입니다. status: {}", requestDto.getStatus());
//            throw new IllegalArgumentException("유효하지 않은 상태 값입니다.");
//        }
//
//        // 상태 변경
//        task.updateStatus(newStatus.getStatusNum());
//
//        log.info("작업 상태가 변경되었습니다. taskId: {}, newStatus: {}", id, newStatus.name());
//
//        return StatusUpdateResponseDto.from(task);
//    }
}










