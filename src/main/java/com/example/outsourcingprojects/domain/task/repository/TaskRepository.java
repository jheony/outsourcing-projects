package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {



}
