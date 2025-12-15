package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.domain.entity.Team;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TeamResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final List<TeamMemberResponseDto> members;

    private TeamResponseDto(Long id, String name, String description, LocalDateTime createdAt, List<TeamMemberResponseDto> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.members = members;
    }

    public static TeamResponseDto of(Team team, List<TeamMemberResponseDto> members) {
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                members
        );
    }
}