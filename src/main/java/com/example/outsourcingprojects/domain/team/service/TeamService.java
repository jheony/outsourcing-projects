package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.entity.TeamMember;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
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

    // 팀 생성
    @Transactional
    public CreateTeamResponseDto createTeam(CreateTeamRequestDto requestDto) {
        // 팀 이름 중복 검사
        if (teamRepository.existsByName(requestDto.getName())) {
            throw new CustomException(ErrorCode.DUPLICATE_TEAM_NAME);
        }

        // 정적 팩토리 메서드로 팀 생성
        Team team = Team.of(requestDto.getName(), requestDto.getDescription());
        // 저장 및 반환
        Team savedTeam = teamRepository.save(team);

        return CreateTeamResponseDto.from(savedTeam);
    }

    // 팀 목록 조회
    @Transactional(readOnly = true)
    public List<TeamResponseDto> getAllTeams() {
        // 삭제되지 않은 팀으로 필터링 후 반환
        return teamRepository.findAll().stream()
                .filter(team -> team.getDeletedAt() == null)
                .map(team -> {
                    List<User> users = userRepository.getUsersByTeam(team.getId()); // 해당 팀에 속한 유저들 조회
                    List<TeamMemberResponseDto> members = users.stream()    // 유저리스트를 TeamMemberResponseDto로
                            .map(TeamMemberResponseDto::from)
                            .collect(Collectors.toList());
                    return TeamResponseDto.of(team, members); // 엔티티와 리스트를 합쳐 DTO 생성
                })
                .collect(Collectors.toList()); // 최종 수집 리스트
    }

    // 팀 상세 조회
    @Transactional(readOnly = true)
    public TeamResponseDto getTeamById(Long id) {
        // id에 해당하는 팀 존재여부 및 삭제여부 확인
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다."));
        List<User> users = userRepository.getUsersByTeam(team.getId()); // 해당하는 팀의 유저 조회
        List<TeamMemberResponseDto> members = users.stream() // user리스트 -> TeamMemberResponseDto
                .map(TeamMemberResponseDto::from)
                .toList();
        return TeamResponseDto.of(team, members);
    }

    // 특정 팀의 멤버 조회
    @Transactional(readOnly = true)
    public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {
        // id에 해당하는 팀 존재여부 및 삭제여부 확인
        teamRepository.findByIdAndDeletedAtIsNull(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        List<User> users = userRepository.getUsersByTeam(teamId); // 해당하는 팀의 유저 조회

        return users.stream()   // user리스트 -> TeamMemberResponseDto
                .map(TeamMemberResponseDto::from)
                .toList();
    }

    // 팀 수정
    @Transactional
    public TeamResponseDto updateTeam(Long id, UpdateTeamRequestDto requestDto, String  userRole) {
        System.out.println("🔥 userRole = [" + userRole + "]");
        if (!"ADMIN".equals(userRole)) {
            throw new CustomException(ErrorCode.NO_UPDATE_PERMISSION);
        }
        // id에 해당하는 팀 존재여부 및 삭제여부 확인
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        team.update(requestDto.getName(), requestDto.getDescription()); // 팀 정보 업데이트
        List<User> users = userRepository.getUsersByTeam(team.getId()); // 업데이트된 팀의 멤버 조회
        List<TeamMemberResponseDto> members = users.stream() // User 리스트 -> DTO로 변환
                .map(TeamMemberResponseDto::from)
                .toList();
        return TeamResponseDto.of(team, members);
    }

    // 팀 삭제
    @Transactional
    public void deleteTeam(Long id) {
        // id에 해당하는 팀 존재여부 및 삭제여부 확인
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

        // 팀에 멤버 존재여부 확인
        List<User> members = userRepository.getUsersByTeam(id);
        if (!members.isEmpty()) {
            throw new CustomException(ErrorCode.TEAM_HAS_MEMBERS);
        }
        // SOFT DELETE 실행(실제 행 삭제X)
        team.delete();
    }

    // 팀 멤버 추가
    @Transactional
    public TeamResponseDto addTeamMember(Long id, Long userId) {
        // 팀 존재여부확인
        Team team = teamRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        // 사용자 존재여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        // 이미 팀 멤버인지 여부확인
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

    // 팀 멤버 제거
    @Transactional
    public void removeTeamMember(Long teamId, Long userId, String userRole) {
        if (!"ADMIN".equals(userRole)) {
            throw new CustomException(ErrorCode.NO_REMOVE_PERMISSION);
        }
        // 팀 존재 여부 확인
        Team team = teamRepository.findByIdAndDeletedAtIsNull(teamId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
        // 팀 멤버 존재 확인
        TeamMember teamMember = teamMemberRepository.findByTeamIdAndUserIdAndDeletedAtIsNull(teamId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.TEAM_MEMBER_NOT_FOUND));

        teamMember.delete();
    }
}