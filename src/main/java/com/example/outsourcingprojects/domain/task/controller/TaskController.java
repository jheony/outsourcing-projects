package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;

import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

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
    public ResponseEntity<Page<CreateTaskResponseDto>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CreateTaskResponseDto> tasks = taskService.getAllTasks(page, size);
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

    // 작업 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId, @RequestParam Long userId) {
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.ok().build();
    }


}
