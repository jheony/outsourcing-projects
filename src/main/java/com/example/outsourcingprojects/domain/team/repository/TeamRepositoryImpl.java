package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.domain.entity.QTeam;
import com.example.outsourcingprojects.domain.entity.Team;
import com.example.outsourcingprojects.domain.search.dto.SearchTeamResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SearchTeamResponse> getSearchTeams(String query) {

        QTeam team = QTeam.team;

        List<Team> teams = queryFactory.select(team)
                .from(team)
                .where(
                        team.name.containsIgnoreCase(query),
                        team.deletedAt.isNull()
                )
                .orderBy(team.createdAt.desc())
                .limit(100)
                .fetch();

        return teams.stream().map(SearchTeamResponse::from).toList();
    }
}