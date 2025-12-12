package com.example.outsourcingprojects.domain.activitylog.service;

import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    // 전체 활동 로그 조회
    public void getAllActivities() {

    }

    // 내 활동 로그 조회
    public void getActivity(Long userId) {
    }
}
