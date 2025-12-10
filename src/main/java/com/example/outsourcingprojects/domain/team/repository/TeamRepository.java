package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByName(String name);

    Optional<Team> findByIdAndDeletedAtIsNull(Long id);
}
