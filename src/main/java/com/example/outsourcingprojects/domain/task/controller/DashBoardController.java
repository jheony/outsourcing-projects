package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.task.service.TempTaskService;
import com.example.outsourcingprojects.domain.task.tempDto.GetTaskSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashBoardController {

    private final TempTaskService tempTaskService;

    //인증인가시, 코드수정
    @GetMapping("/api/dashboard/tasks")
    public GlobalResponse<GetTaskSummaryResponse> GetSummaryTasks(Long userId) {
        GetTaskSummaryResponse result = tempTaskService.GetTaskSummaries(userId);

        return GlobalResponse.success("내 작업 요약 조회 성공", result);
    }
}