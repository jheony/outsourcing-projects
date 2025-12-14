package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.request.UpdateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.dto.response.TeamResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.teammember.repository.TeamMemberRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamService teamService;

    // 팀 생성 단위테스트 진행
    @Test
    @DisplayName("팀 생성 성공")
    void createTeam_success() {

        // given
        CreateTeamRequestDto request = new CreateTeamRequestDto();
        ReflectionTestUtils.setField(request, "name", "테스트팀");
        ReflectionTestUtils.setField(request, "description", "테스트 설명입니다.");

        Team testTeam = Team.of("테스트팀", "테스트 설명입니다.");
        ReflectionTestUtils.setField(testTeam, "id", 1L);

        when(teamRepository.existsByNameAndDeletedAtIsNull(request.getName())).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);

        // when
        CreateTeamResponseDto result = teamService.createTeam(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트팀");
        assertThat(result.getDescription()).isEqualTo("테스트 설명입니다.");

        verify(teamRepository, times(1)).save(any(Team.class));
    }

    // 팀 목록 조회 단위테스트 진행
    @Test
    @DisplayName("팀 목록 조회 성공")
    void getAllTeams() {

        // given
        Team testTeam = Team.of("테스트팀", "테스트 설명");
        ReflectionTestUtils.setField(testTeam, "id", 1L);

        List<Team> testTeamList = new ArrayList<>();
        testTeamList.add(testTeam);

        when(teamRepository.findAll()).thenReturn(testTeamList);

        // when
        List<TeamResponseDto> result = teamService.getAllTeams();

        // then
        assertThat(result.get(0).getName()).isEqualTo("테스트팀");
    }

    // 팀 상세 조회 단위테스트 진행
    @Test
    @DisplayName("팀 상세 조회 성공")
    void getTeamById() {

        // given
        Team testTeam = Team.of("테스트팀", "테스트 설명");
        ReflectionTestUtils.setField(testTeam, "id", 1L);

        when(teamRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.getUsersByTeam(1L)).thenReturn(new ArrayList<>());
        // when
        TeamResponseDto result = teamService.getTeamById(1L);

        // then
        assertThat(result.getName()).isEqualTo("테스트팀");
    }




    @Test
    @DisplayName("팀 수정 성공")
    void updateTeam() {

        // given
        Team testTeam = Team.of("테스트팀", "테스트 설명");
        ReflectionTestUtils.setField(testTeam, "id", 1L);

        UpdateTeamRequestDto request = new UpdateTeamRequestDto();
        ReflectionTestUtils.setField(request, "name", "팀수정 테스트");
        ReflectionTestUtils.setField(request, "description", "수정테스트 설명입니다.");

        when(teamRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.getUsersByTeam(1L)).thenReturn(new ArrayList<>());

        // when
        TeamResponseDto result = teamService.updateTeam(1L, request, "ADMIN");

        // then
        assertThat(result.getName()).isEqualTo("팀수정 테스트");
        assertThat(result.getDescription()).isEqualTo("수정테스트 설명입니다.");
    }


}