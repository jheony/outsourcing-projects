package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.common.aop.Loggable;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.task.dto.*;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // 작업 생성
    @Loggable
    @PostMapping
    public GlobalResponse<CreateTaskResponse> createTaskHandler(
            @RequestBody CreateTaskRequest request, HttpServletRequest httpServletRequest
    ) {
        Long userId = (Long) httpServletRequest.getAttribute("userId");
        CreateTaskResponse response = taskService.createTask(request, userId);
        return GlobalResponse.success("작업이 생성되었습니다.", response);
    }

    // 전체 작업(목록) 조회
    @GetMapping
    public GlobalResponse<PageDataDTO<TaskDTO>> getAllTasksHandler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long assigneeId
    ) {
        Pageable pageable = PageRequest.of(page, size);

        PageDataDTO<TaskDTO> result = taskService.getAllTasks(status, query, assigneeId, pageable);

        return GlobalResponse.success("작업 목록 조회 성공", result);
    }

    // 작업 상세 조회
    @GetMapping("/{taskId}")
    public GlobalResponse<TaskDTO> getTaskHandler(@PathVariable Long taskId) {

        TaskDTO result = taskService.getTask(taskId);

        return GlobalResponse.success("작업 조회 성공", result);
    }

    // 작업 수정
    @Loggable
    @PutMapping("/{taskId}")
    public GlobalResponse<UpdateTaskResponse> updateTaskHandler(
            @PathVariable Long taskId, @RequestBody UpdateTaskRequest request, HttpServletRequest httpRequest
    ) {
        Long userId = (Long) httpRequest.getAttribute("userId");

        UpdateTaskResponse response = taskService.updateTask(taskId, request, userId);

        return GlobalResponse.success("작업이 수정되었습니다.", response);

    }

    // 작업 삭제
    @Loggable
    @DeleteMapping("/{taskId}")
    public GlobalResponse<Void> deleteTaskHandler(
            @PathVariable Long taskId, HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("userId");

        taskService.deleteTask(taskId, userId);

        return GlobalResponse.success("작업이 삭제되었습니다.", null);
    }

    // 작업 상태 변경
    @Loggable
    @PatchMapping("/{taskId}/status")
    public GlobalResponse<StatusUpdateResponse> updateTaskStatusHandler(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskStatusRequest requestDto
    ) {
        StatusUpdateResponse response = taskService.statusUpdateTask(taskId, requestDto);

        return GlobalResponse.success("작업 상태가 변경되었습니다.", response);
    }
}