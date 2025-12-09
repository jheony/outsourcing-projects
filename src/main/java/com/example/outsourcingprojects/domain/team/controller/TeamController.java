package com.example.outsourcingprojects.domain.team.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    // 팀 생성
    @PostMapping
    public GlobalResponse<CreateTeamResponseDto> createTeam(@Valid @RequestBody CreateTeamRequestDto request) {
        CreateTeamResponseDto responseDto = teamService.createTeam(request);
        return GlobalResponse.success("팀이 생성되었습니다", responseDto);
    }
}
