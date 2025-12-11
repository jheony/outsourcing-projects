package com.example.outsourcingprojects.common.entity;

import com.example.outsourcingprojects.domain.team.dto.response.TeamMemberResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    private TeamMember(Team team, User user) {
        //필요없어요 Why? 상단의 @NoArgsConstructor를 통해서 이 생성자의 역할을 대신 하고 있습니다.
        this.team = team;
        this.user = user;
    }
    //팀을 만들때 사용해주면 편리합니다.
    public static TeamMember from(Team team, User user) {
        return new TeamMember(team, user);
    }
}
