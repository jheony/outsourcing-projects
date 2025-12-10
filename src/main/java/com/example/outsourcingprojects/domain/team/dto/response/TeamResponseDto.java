package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamResponseDto {
    private  Long id;
    private  String name;
    private  String description;
    private LocalDateTime createdAt;
    private List<TeamMemberResponseDto> members;

    private TeamResponseDto(Long id, String name, String description, LocalDateTime createdAt, List<TeamMemberResponseDto> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.members = members;
    }

    public static TeamResponseDto from(Team team) {
        List<TeamMemberResponseDto> members = team.getTeamMembers().stream()
                .filter(teamMember -> teamMember.getDeletedAt() == null)
                .map(teamMember -> TeamMemberResponseDto.from(teamMember.getUser()))
                .collect(Collectors.toList());

        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getDescription(),
                team.getCreatedAt(),
                members
        );
    }
}
