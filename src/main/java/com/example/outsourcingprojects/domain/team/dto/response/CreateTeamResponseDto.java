package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.domain.entity.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CreateTeamResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<TeamMemberResponseDto> members;

    public static CreateTeamResponseDto from(Team team) {
        return new CreateTeamResponseDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                Collections.emptyList()
        );
    }
}