package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    boolean existsByName(String name);
}
