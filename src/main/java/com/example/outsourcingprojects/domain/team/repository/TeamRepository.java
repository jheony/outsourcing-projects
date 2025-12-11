package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.common.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {

    boolean existsByName(String name);

    Optional<Team> findByIdAndDeletedAtIsNull(Long id);
}
