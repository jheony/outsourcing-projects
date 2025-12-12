package com.example.outsourcingprojects.domain.activitylog.service;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.domain.activitylog.dto.MyActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.dto.UserActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    // 전체 활동 로그 조회
    public void getAllActivityLogs() {

    }

    // 내 활동 로그 조회
    public PageDataDTO<MyActivityLogResponse> getActivityLogs(Long userId, int page, int size, Pageable pageable) {

        Page<ActivityLog> activityLogs = activityLogRepository.getActivityLogs(userId, pageable);

        Page<MyActivityLogResponse> result = activityLogs
                .map(activityLog -> MyActivityLogResponse.from(activityLog, UserActivityLogResponse.from(activityLog.getUser())));

        return PageDataDTO.of(result);
    }
}
