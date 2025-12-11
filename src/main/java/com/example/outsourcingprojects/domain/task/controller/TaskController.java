package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    //Controller에서 사용하는 메서드는 서비스의 메서드명과 곂치지 않도록 Handler를 붙여주기로 약속했습니다. ok
    private final TaskService taskService;
    //TODO ResposneEntity  대신 GrobalResponse 로 바꿔서 , return GrobalResponse.success로 (다른팀원 controller 참고)

    // 작업 생성
    @PostMapping
    public GlobalResponse<CreateTaskResponseDto> createTaskHandler(
            @RequestBody CreateTaskRequestDto request
    ) {
        CreateTaskResponseDto response = taskService.createTask(request);
        return GlobalResponse.success("작업 생성이 성공하였습니다", response);
    }

    // 작업 전체 조회
    @GetMapping
    public GlobalResponse<PageDataDTO<CreateTaskResponseDto>> getAllTasksHandler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageDataDTO<CreateTaskResponseDto> tasks = taskService.getAllTasks(page, size);
        return GlobalResponse.success("작업 목록 조회가 성공하였습니다", tasks);
    }

    // 특정 작업 조회
    @GetMapping("/{taskId}")
    public GlobalResponse<CreateTaskResponseDto> getTaskByIdHandler(@PathVariable Long taskId) {
        CreateTaskResponseDto task = taskService.getTaskById(taskId);
        return GlobalResponse.success("작업 상세 조회가 성공하였습니다", task);
    }

    // 작업 수정
    @PutMapping("/{taskId}")
    public GlobalResponse<CreateTaskResponseDto> updateTaskHandler(
            @PathVariable Long taskId, @RequestBody CreateTaskRequestDto request, @RequestParam Long userId) {
        CreateTaskResponseDto response = taskService.updateTask(taskId, request, userId);
        return GlobalResponse.success("작업 수정이 성공하였습니다", response);

    }

    // 작업 삭제
    @DeleteMapping("/{taskId}")
    public GlobalResponse<Void> deleteTaskHandler(
            @PathVariable Long taskId, @RequestParam Long userId) {
        taskService.deleteTask(taskId, userId);
        return GlobalResponse.success("작업 삭제가 성공하였습니다", null);
    }


}
