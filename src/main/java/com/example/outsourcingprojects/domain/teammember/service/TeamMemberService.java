package com.example.outsourcingprojects.domain.teammember.service;

import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.domain.entity.Team;
import com.example.outsourcingprojects.domain.entity.TeamMember;
import com.example.outsourcingprojects.domain.entity.User;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.teammember.repository.TeamMemberRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    // 특정 팀의 멤버 조회
    @Transactional(readOnly = true)
    public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {

        teamRepository.findByIdAndDeletedAtIsNull(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        List<User> users = userRepository.getUsersByTeam(teamId);

        return users.stream()
                .map(TeamMemberResponseDto::from)
                .toList();
    }

    // 팀 멤버 제거
    @Transactional
    public void deleteTeamMember(Long teamId, Long userId, String userRole) {

        if (!"ADMIN".equals(userRole)) {
            throw new CustomException(ErrorCode.NO_REMOVE_PERMISSION);
        }

        if (!teamRepository.existsByIdAndDeletedAtIsNull(teamId)) {
            throw new CustomException(ErrorCode.TEAM_NOT_FOUND);
        }

        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserIdAndDeletedAtIsNull(teamId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        teamMember.delete();
    }

    // 팀 멤버 추가
    @Transactional
    public TeamResponseDto addTeamMember(Long id, Long userId) {

        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean isTeamMember = teamMemberRepository.existsByTeamIdAndUserIdAndDeletedAtIsNull(id, userId);

        if (isTeamMember) {
            throw new CustomException(ErrorCode.ALREADY_TEAM_MEMBER);
        }

        TeamMember teamMember = TeamMember.from(team, user);
        teamMemberRepository.save(teamMember);

        List<User> users = userRepository.getUsersByTeam(team.getId());

        List<TeamMemberResponseDto> members = users.stream()
                .map(TeamMemberResponseDto::from)
                .toList();

        return TeamResponseDto.of(team, members);
    }
}