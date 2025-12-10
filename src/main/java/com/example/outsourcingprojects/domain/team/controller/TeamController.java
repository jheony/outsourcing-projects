package com.example.outsourcingprojects.domain.team.controller;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
//import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 팀 목록 조회
    @GetMapping
    public GlobalResponse<List<TeamResponseDto>> getAllTeams() {
        List<TeamResponseDto> teams = teamService.getAllTeams();
        return GlobalResponse.success("팀 목록 조회 성공", teams);
    }

    // 팀 상세 조회
    @GetMapping("/{id}")
    public GlobalResponse<TeamResponseDto> getTeamById(@PathVariable Long id) {
        TeamResponseDto team = teamService.getTeamById(id);
        return GlobalResponse.success("팀 멤버 조회 성공", team);
    }

}
