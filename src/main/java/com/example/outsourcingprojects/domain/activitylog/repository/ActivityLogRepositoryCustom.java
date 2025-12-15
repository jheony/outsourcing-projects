package com.example.outsourcingprojects.domain.activitylog.repository;

import com.example.outsourcingprojects.domain.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ActivityLogRepositoryCustom {

    // 내 활동 로그 조회
    Page<ActivityLog> getMyActivityLogs(Long userId, Pageable pageable);

    // 조건 포함 전체 활동 로그 조회
    Page<ActivityLog> getAllActivityLogsWithCondition(Long typeNum, Long taskId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // 전체 활동 로그 조회
    Page<ActivityLog> getAllActivityLogs(Pageable pageable);
}