package com.example.outsourcingprojects.domain.activitylog.controller;

import com.example.outsourcingprojects.domain.activitylog.service.ActivityLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public void getAllActivitiesHandler() {
        activityLogService.getAllActivities();
    }

    @GetMapping("/me")
    public void getActivityHandler(HttpServletRequest request,
                                   @RequestParam(required = false, defaultValue = "0") Long page,
                                   @RequestParam(required = false, defaultValue = "10") Long size) {

        Long userId = (Long) request.getAttribute("userId");

        activityLogService.getActivity(userId);
    }
}
