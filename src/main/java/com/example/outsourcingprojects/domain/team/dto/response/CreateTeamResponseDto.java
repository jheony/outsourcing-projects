package com.example.outsourcingprojects.domain.team.dto.response;

import com.example.outsourcingprojects.common.entity.Team;
import com.example.outsourcingprojects.common.entity.User;
import io.jsonwebtoken.lang.Collections;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CreateTeamResponseDto {

    private  Long id;
    private  String name;
    private  String description;
    private  LocalDateTime createdAt;
    private  List<User> members;

    // 생성자
    private CreateTeamResponseDto(Long id, String name, String description, LocalDateTime createdAt, List<User> members) {
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

