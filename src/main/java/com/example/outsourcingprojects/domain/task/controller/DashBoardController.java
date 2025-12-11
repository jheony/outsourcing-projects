package com.example.outsourcingprojects.domain.task.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.task.service.TempTaskService;
import com.example.outsourcingprojects.domain.task.tempDto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.task.tempDto.DashBoardDTO;
import com.example.outsourcingprojects.domain.task.tempDto.GetTaskSummaryResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashBoardController {

    private final TempTaskService tempTaskService;

    //인증인가시, 코드수정
    @GetMapping("/api/dashboard/tasks")
    public GlobalResponse<GetTaskSummaryResponse> getSummaryTasksHandler(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        GetTaskSummaryResponse result = tempTaskService.getTaskSummaries(userId);

        return GlobalResponse.success("내 작업 요약 조회 성공", result);
    }

    @GetMapping("/api/dashboard/stats")
    public GlobalResponse<DashBoardDTO> getDashBoardHandler(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        DashBoardDTO result = tempTaskService.getDashBoard(userId);

        return GlobalResponse.success("대시보드 통계 조회 성공",result);
    }

    @GetMapping("/api/dashboard/weekly-trend")
    public GlobalResponse<List<DailyTaskDTO>> getWeeklyTasksHandler(HttpServletRequest request){

        Long userId = (Long) request.getAttribute("userId");

        List<DailyTaskDTO> result = tempTaskService.getWeeklyTasks(userId);

        return GlobalResponse.success("주간 작업 추세 조회 성공", result);
    }
}