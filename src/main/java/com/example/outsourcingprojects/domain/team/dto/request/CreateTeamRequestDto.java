package com.example.outsourcingprojects.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTeamRequestDto {

    @NotBlank(message = "팀 이름은 필수입니다")
    @Size(max = 100, message = "100자를 초과할 수 없습니다")
    private String name;

    @Size(max = 255, message = "255자를 초과할 수 없습니다.")
    private String description;

    private CreateTeamRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static CreateTeamRequestDto of(String name, String description) {
        return new CreateTeamRequestDto(name, description);
    }
}
