package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.domain.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
    Page<Task> findAll(Pageable pageable);

    @Override
    @Modifying
    @Transactional
    <S extends Task> List<S> saveAll(Iterable<S> entities);

    @Modifying
    @Query("""
                UPDATE Task t
                SET t.deletedAt = :now
                WHERE t.id = :id
            """)
    void softDelete(@Param("id") Long id,
                    @Param("now") LocalDateTime now);

    Optional<Task> findByIdAndDeletedAtIsNull(Long id);
}
