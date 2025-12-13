package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.task.dto.*;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import com.example.outsourcingprojects.domain.task.service.TempTaskService;
import jakarta.servlet.http.HttpServletRequest;
import com.example.outsourcingprojects.domain.task.service.TempTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TempTaskService tempTaskService;
    //TODO ResposneEntity  대신 GrobalResponse 로 바꿔서 , return GrobalResponse.success로 (다른팀원 controller 참고)

    // 작업 생성
    @PostMapping
    public GlobalResponse<CreateTaskResponseDto> createTaskHandler(
            @RequestBody CreateTaskRequestDto request, HttpServletRequest httpServletRequest
    ) {
        CreateTaskResponseDto response = tempTaskService.createTask(request);
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        CreateTaskResponseDto response = tempTaskService.createTask(request, userId);
        return GlobalResponse.success("작업이 생성되었습니다.", response);
    }

    // 전체 작업(목록) 조회
    @GetMapping
    public GlobalResponse<PageDataDTO<TaskListResponseDto>> getAllTasksHandler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId
    ) {
        Pageable pageable = PageRequest.of(page, size);

        PageDataDTO<TaskListResponseDto> result = taskService.getAllTasks(status, query, assigneeId, pageable);

        return GlobalResponse.success("작업 목록 조회 성공", result);
    }

    // 작업 상세 조회
    @GetMapping("/{taskId}")
    public GlobalResponse<TaskResponse> getTaskHandler(@PathVariable Long taskId) {

        TaskResponse result = taskService.getTask(taskId);

        return GlobalResponse.success("작업 조회 성공", result);
    }

    // 작업 수정
    @PutMapping("/{taskId}")
    public GlobalResponse<UpdateTaskResponse> updateTaskHandler(
            @PathVariable Long taskId, @RequestBody UpdateTaskRequest request, HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        UpdateTaskResponse response = tempTaskService.updateTask(taskId, request, userId);
        return GlobalResponse.success("작업이 수정되었습니다.", response);

    }

    // 작업 삭제
    @DeleteMapping("/{taskId}")
    public GlobalResponse<Void> deleteTaskHandler(
            @PathVariable Long taskId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        tempTaskService.deleteTask(taskId, userId);
        return GlobalResponse.success("작업이 삭제되었습니다.", null);
    }

    //    // 작업 상태 변경
    @PatchMapping("/{taskId}/status")
    public GlobalResponse<StatusUpdateResponseDto> updateTaskStatusHandler(
            @PathVariable Long taskId,
            @RequestBody StatusUpdateRequestDto requestDto
    ) {
        StatusUpdateResponseDto response = tempTaskService.statusUpdateTask(taskId, requestDto);
        return GlobalResponse.success("작업 상태가 변경되었습니다.", response);
    }


}
