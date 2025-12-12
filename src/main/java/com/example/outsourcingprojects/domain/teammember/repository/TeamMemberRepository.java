package com.example.outsourcingprojects.domain.teammember.repository;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {


    boolean existsByTeamIdAndUserIdAndDeletedAtIsNull(Long id, Long userId);

    Optional<TeamMember> findByTeamIdAndUserIdAndDeletedAtIsNull(Long teamId, Long userId);
}


