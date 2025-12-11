package com.example.outsourcingprojects.domain.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateTeamRequestDto {

    @NotBlank(message = "팀 이름은 필수입니다")
    @Size(max = 100, message = "100자를 초과할 수 없습니다")
    private String name;

    @Size(max = 255, message = "255자를 초과할 수 없습니다.")
    private String description;

    private UpdateTeamRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static UpdateTeamRequestDto of(String name, String description) {
        //정적 팩토리 메서드를 리퀘스트에서 사용하는걸 처음보네요
        //혹시 학습하신 곳이 있으시다면 공유 부탁드립니다.
        //정적 팩토리 메서드를 작성하셨는데 활용하지 않고 계시네요.
        //해당 DTO를 new로 생성하고 있는 곳에서 메서드 그대로 활용하시면 됩니다.
        //ex ) UpdateTeamRequestDto.of(name,description)
        return new UpdateTeamRequestDto(name, description);
    }
}
