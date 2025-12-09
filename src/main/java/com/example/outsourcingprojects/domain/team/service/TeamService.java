package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public CreateTeamResponseDto createTeam(CreateTeamRequestDto requestDto) {
        // 팀 이름 중복 검사
        if(teamRepository.existsByName(requestDto.getName())) {
            throw new IllegalArgumentException("존재하는 팀 이름입니다.");
        }

        // 정적 팩토리 메서드로 팀 생성
        Team team = Team.of(requestDto.getName(), requestDto.getDescription());

        // 저장
        Team savedTeam = teamRepository.save(team);

        // 정적 팩토리 메소드 응답 DTO 생성 및 반환
        return CreateTeamResponseDto.from(savedTeam);

    }
}
