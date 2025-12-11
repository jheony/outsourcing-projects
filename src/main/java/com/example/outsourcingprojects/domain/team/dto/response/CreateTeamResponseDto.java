package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.Team;

import java.util.Collections;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateTeamResponseDto {
    //response 응답객체는
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<TeamMemberResponseDto> members;

    // 생성자
    private CreateTeamResponseDto(Long id, String name, String description, LocalDateTime createdAt, List<TeamMemberResponseDto> members) {
        //위의 @NoArgsConstructor 가 이 생성자의 역할을 대신 하고있습니다.
        //필요 없는 생성자 입니다.
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.members = members;
    }

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

