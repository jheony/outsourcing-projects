package com.example.outsourcingprojects.domain.dashboard.repository;

import com.example.outsourcingprojects.common.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashBoardRepository extends JpaRepository<Task, Long>, DashBoardRepositoryCustom {
}