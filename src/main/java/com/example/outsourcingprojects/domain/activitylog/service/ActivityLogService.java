package com.example.outsourcingprojects.domain.activitylog.service;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.model.ActivityType;
import com.example.outsourcingprojects.common.util.dto.PageDataDTO;
import com.example.outsourcingprojects.domain.activitylog.dto.response.ActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.dto.response.MyActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.dto.response.UserActivityLogResponse;
import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    // 전체 활동 로그 조회
    public PageDataDTO<ActivityLogResponse> getAllActivityLogs(String type, Long taskId, String startDate, String endDate, Pageable pageable) {

        boolean searchCondition = searchCondition(type, taskId, startDate, endDate);

        Long typeNum = (type != null) ? ActivityType.valueOf(type).getActivityNum() : null;
        LocalDateTime startDateTime = (startDate != null) ? LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(0, 0, 0) : null;
        LocalDateTime endDateTime = (endDate != null) ? LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59) : null;

        Page<ActivityLog> activityLogs;

        if (searchCondition) {
            activityLogs = activityLogRepository.getAllActivityLogsWithCondition(typeNum, taskId, startDateTime, endDateTime, pageable);
        } else {
            activityLogs = activityLogRepository.getAllActivityLogs(pageable);
        }

        Page<ActivityLogResponse> result = activityLogs
                .map(activityLog -> ActivityLogResponse.from(activityLog, UserActivityLogResponse.from(activityLog.getUser())));

        return PageDataDTO.of(result);
    }

    // 내 활동 로그 조회
    public PageDataDTO<MyActivityLogResponse> getActivityLogs(Long userId, Pageable pageable) {

        Page<ActivityLog> activityLogs = activityLogRepository.getMyActivityLogs(userId, pageable);

        Page<MyActivityLogResponse> result = activityLogs
                .map(activityLog -> MyActivityLogResponse.from(activityLog, UserActivityLogResponse.from(activityLog.getUser())));

        return PageDataDTO.of(result);
    }

    // 조건 하나라도 있는지 체크
    private boolean searchCondition(String type, Long taskId, String startDate, String endDate) {
        return (type != null) || (taskId != null) || (startDate != null) || (endDate != null);
    }
}