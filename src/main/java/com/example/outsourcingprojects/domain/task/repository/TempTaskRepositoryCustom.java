package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.Task;
import org.springframework.data.domain.Page;


public interface TempTaskRepositoryCustom {
    //담당자 아이디와 작업 상태를 기준으로 조회
    Page<Task> findAllByAssigneeIdAndStatus(Long assigneeId, Long status);
}