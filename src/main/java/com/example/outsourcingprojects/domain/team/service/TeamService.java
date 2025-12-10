package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.request.UpdateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.teammember.repository.TeamMemberRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public CreateTeamResponseDto createTeam(CreateTeamRequestDto requestDto) {
        // 팀 이름 중복 검사
        if (teamRepository.existsByName(requestDto.getName())) {
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
                .map(team -> {
                    List<User> users = userRepository.getUsersByTeam(team.getId());
                    List<TeamMemberResponseDto> members = users.stream()
                            .map(TeamMemberResponseDto::from)
                            .collect(Collectors.toList());
                    return TeamResponseDto.of(team, members);
                })
                .collect(Collectors.toList());
    }

    // 팀 상세 조회
    @Transactional(readOnly = true)
    public TeamResponseDto getTeamById(Long id) {
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> users = userRepository.getUsersByTeam(team.getId());
        List<TeamMemberResponseDto> members = users.stream()
                .map(TeamMemberResponseDto::from)
                .toList();
        return TeamResponseDto.of(team, members);
    }

    // 팀 멤버 조회
    @Transactional(readOnly = true)
    public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {
        Team team = teamRepository.findByIdAndDeletedAtIsNull(teamId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        List<User> users = userRepository.getUsersByTeam(teamId);

        return users.stream()
                .map(TeamMemberResponseDto::from)
                .toList();
    }

    // 팀 수정
    @Transactional
    public TeamResponseDto updateTeam(Long id, UpdateTeamRequestDto requestDto) {
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));

        team.update(requestDto.getName(), requestDto.getDescription());
        List<User> users = userRepository.getUsersByTeam(team.getId());
        List<TeamMemberResponseDto> members = users.stream()
                .map(TeamMemberResponseDto::from)
                .toList();
        return TeamResponseDto.of(team, members);
    }
}
