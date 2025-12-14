package com.example.outsourcingprojects.domain.teammember.service;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


class TeamMemberServiceTest {


    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TeamMemberService teamMemberService;



    // 팀 멤버 조회
    @Test
    @DisplayName("팀 멤버 조회 성공")
    void getTeamMembers_success() {

        // given
        Team testTeam = Team.of("테스트팀", "테스트 설명");
        ReflectionTestUtils.setField(testTeam, "id", 1L);


        when(teamRepository.findByIdAndDeletedAtIsNull(1L)).thenReturn(Optional.of(testTeam));
        when(userRepository.getUsersByTeam(1L)).thenReturn(new ArrayList<>());

        // when
        List<TeamMemberResponseDto> result = teamMemberService.getTeamMembers(1L);

        // then
        assertThat(result).isNotNull();
    }
}