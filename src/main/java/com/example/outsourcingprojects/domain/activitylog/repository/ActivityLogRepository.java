package com.example.outsourcingprojects.domain.activitylog.repository;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
