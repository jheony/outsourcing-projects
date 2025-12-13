package com.example.outsourcingprojects.domain.team.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UpdateTeamRequestDto {

    @Size(max = 100, message = "100자를 초과할 수 없습니다")
    private String name;

    @Size(max = 255, message = "255자를 초과할 수 없습니다.")
    private String description;
}