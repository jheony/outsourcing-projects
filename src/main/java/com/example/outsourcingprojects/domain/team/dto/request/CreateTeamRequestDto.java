package com.example.outsourcingprojects.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateTeamRequestDto {

    @NotBlank
    private String name;
    private String description;
}
