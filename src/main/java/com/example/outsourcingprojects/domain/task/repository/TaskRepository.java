package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    //레포지토리의 상단에는 레포지토리임을 표시해주는 어노테이션을 사용해주어야 합니다.
    //@Repository는 클래스가 데이터베이스와 상호작용하는 객체임을 나타내는 마커 어노테이션입니다.
    // 페이징 전체 조회
    Page<Task> findAll(Pageable pageable);

    @Override
    @Modifying
    @Transactional
    <S extends Task> List<S> saveAll(Iterable<S> entities);
}
