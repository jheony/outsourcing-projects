package com.example.outsourcingprojects.domain.activitylog.controller;

import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.activitylog.dto.MyActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.service.ActivityLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping
    public void getAllActivityLogsHandler() {
        activityLogService.getAllActivityLogs();
    }

    @GetMapping("/me")
    public GlobalResponse<PageDataDTO<MyActivityLogResponse>> getActivityLogsHandler(HttpServletRequest request,
                                                                                     @RequestParam(required = false, defaultValue = "0") int page,
                                                                                     @RequestParam(required = false, defaultValue = "10") int size
    ) {

        Long userId = (Long) request.getAttribute("userId");

        Pageable pageable = PageRequest.of(page, size);

        PageDataDTO<MyActivityLogResponse> result = activityLogService.getActivityLogs(userId, page, size, pageable);

        return GlobalResponse.success("내 활동 로그 조회 성공", result);
    }
}
