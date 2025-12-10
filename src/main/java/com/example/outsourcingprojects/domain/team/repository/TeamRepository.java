package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.user.repository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, UserRepositoryCustom {

    boolean existsByName(String name);

    Optional<Team> findByIdAndDeletedAtIsNull(Long id);


}
