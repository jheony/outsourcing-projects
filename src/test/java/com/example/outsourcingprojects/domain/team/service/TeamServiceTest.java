package com.example.outsourcingprojects.domain.team.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.team.dto.request.CreateTeamRequestDto;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  TeamMemberRepository teamMemberRepository;

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

        when(teamRepository.existsByName(request.getName())).thenReturn(false);
        when(teamRepository.save(any(Team.class))).thenReturn(testTeam);

        // when
        CreateTeamResponseDto result = teamService.createTeam(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("테스트팀");
        assertThat(result.getDescription()).isEqualTo("테스트 설명입니다.");

        verify(teamRepository, times(1)).save(any(Team.class));
    }
}