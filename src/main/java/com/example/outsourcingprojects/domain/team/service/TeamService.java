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
        Team team = new Team(requestDto.getName(), requestDto.getDescription());

        Team createdTeam = teamRepository.save(team);

        return new CreateTeamResponseDto(createdTeam.getId(), createdTeam.getName(), createdTeam.getDescription(), createdTeam.getCreatedAt());
    }
}
