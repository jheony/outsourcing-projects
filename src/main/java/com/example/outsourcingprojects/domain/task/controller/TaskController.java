package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    //Controller에서 사용하는 메서드는 서비스의 메서드명과 곂치지 않도록 Handler를 붙여주기로 약속했습니다.
    private final TaskService taskService;

    // 작업 생성
    @PostMapping
    public ResponseEntity<CreateTaskResponseDto> createTask(
            @RequestBody CreateTaskRequestDto request
    ) {
        CreateTaskResponseDto response = taskService.createTask(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 작업 전체 조회
    @GetMapping
    public ResponseEntity<List<CreateTaskResponseDto>> getAllTasks() {
        List<CreateTaskResponseDto> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // 특정 작업 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<CreateTaskResponseDto> getTaskById(@PathVariable Long taskId) {
        CreateTaskResponseDto task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(task);
    }

    // 작업 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<CreateTaskResponseDto> updateTask(
            @PathVariable Long taskId, @RequestBody CreateTaskRequestDto request, @RequestParam Long userId) {
        CreateTaskResponseDto response = taskService.updateTask(taskId, request, userId);
        return ResponseEntity.ok(response);

    }


}
