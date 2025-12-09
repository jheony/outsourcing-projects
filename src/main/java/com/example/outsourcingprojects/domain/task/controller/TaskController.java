package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
import com.example.outsourcingprojects.domain.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
