package com.example.outsourcingprojects.common.entity;

import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column
    private String description;


    private Team(String name, String description) {
        //필요없어요 Why? 상단의 @NoArgsConstructor를 통해서 이 생성자의 역할을 대신 하고 있습니다.
        this.name = name;
        this.description = description;
    }

    public static Team of(String name, String description) {
        return new Team(name, description);
    }


    public void update(String name, String description) {
        if (name != null) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }
}
