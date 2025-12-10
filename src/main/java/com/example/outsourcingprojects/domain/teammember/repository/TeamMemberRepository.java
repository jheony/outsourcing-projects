package com.example.outsourcingprojects.domain.teammember.repository;

import com.example.outsourcingprojects.common.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}

