package com.example.outsourcingprojects.domain.team.controller;

import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.team.dto.request.AddTeamMemberRequestDto;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.request.UpdateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.service.TeamService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
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
    public GlobalResponse<CreateTeamResponseDto> createTeamHandler(@Valid @RequestBody CreateTeamRequestDto request) {
        CreateTeamResponseDto responseDto = teamService.createTeam(request);
        return GlobalResponse.success("팀이 생성되었습니다", responseDto);
    }

    // 팀 목록 조회
    @GetMapping
    public GlobalResponse<List<TeamResponseDto>> getAllTeamsHandler() {
        List<TeamResponseDto> teams = teamService.getAllTeams();
        return GlobalResponse.success("팀 목록 조회 성공", teams);
    }

    // 팀 상세 조회
    @GetMapping("/{id}")
    public GlobalResponse<TeamResponseDto> getTeamByIdHandler(@PathVariable Long id) {
        TeamResponseDto team = teamService.getTeamById(id);
        return GlobalResponse.success("팀 멤버 조회 성공", team);
    }

    // 특정 팀의 멤버 조회
    @GetMapping("/{teamId}/members")
    public GlobalResponse<List<TeamMemberResponseDto>> getTeamMembersHandler(@PathVariable Long teamId) {
        List<TeamMemberResponseDto> members = teamService.getTeamMembers(teamId);
        return GlobalResponse.success("팀 멤버 조회 성공", members);
    }

    // 팀 수정
    @PutMapping("/{id}")
    public GlobalResponse<TeamResponseDto> updateTeamHandler(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTeamRequestDto request,
            HttpServletRequest userToken) {
        String userRole = (String) userToken.getAttribute("role");
        TeamResponseDto responseDto = teamService.updateTeam(id, request, userRole);
        return GlobalResponse.success("팀 정보가 수정되었습니다.", responseDto);
    }


    // 팀 삭제
    @DeleteMapping("/{id}")
    public GlobalResponse<Void> deleteTeamHandler(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return GlobalResponse.success("팀이 삭제되었습니다.", null);
    }

    // 팀 멤버 추가
    @PostMapping("/{teamId}/members")
    public GlobalResponse<TeamResponseDto> addTeamMemberHandler(@PathVariable Long teamId, @Valid @RequestBody AddTeamMemberRequestDto request) {
        TeamResponseDto responseDto = teamService.addTeamMember(teamId, request.getUserId());
        return GlobalResponse.success("팀 멤버가 추가되었습니다.", responseDto);
    }

    // 팀 멤버 제거
    @DeleteMapping("/{teamId}/members/{userId}")
    public GlobalResponse<Void> removeTeamMemberHandler(
            @PathVariable Long teamId,
            @PathVariable Long userId,
            HttpServletRequest userToken) {
        String userRole = (String) userToken.getAttribute("role");
        teamService.removeTeamMember(teamId, userId, userRole);
        return GlobalResponse.success("팀 멤버가 제거되었습니다.", null);
    }
}