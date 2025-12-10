package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 팀 목록 조회
    @Transactional(readOnly = true)
    public List<TeamResponseDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .filter(team -> team.getDeletedAt() == null)
                .map(TeamResponseDto::from)
                .toList();
    }

    // 팀 상세 조회
    @Transactional(readOnly = true)
    public TeamResponseDto getTeamById(Long id) {
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        return TeamResponseDto.from(team);
    }
}
