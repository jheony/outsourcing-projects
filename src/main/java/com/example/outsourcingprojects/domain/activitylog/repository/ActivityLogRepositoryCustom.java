package com.example.outsourcingprojects.domain.activitylog.repository;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogRepositoryCustom {

    Page<ActivityLog> getActivityLogs(Long userId, Pageable pageable);
}