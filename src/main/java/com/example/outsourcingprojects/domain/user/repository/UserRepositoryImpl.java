package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.common.entity.*;
import com.example.outsourcingprojects.domain.search.dto.SearchUserResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> getUsersByTeam(Long teamId) {

        QTeamMember teamMember = QTeamMember.teamMember;
        QUser user = QUser.user;
        List<User> teamUsers = queryFactory.select(user)
                .from(teamMember)
                .join(teamMember.user, user)
                .where(user.deletedAt.isNull().and(teamMember.deletedAt.isNull()).and(teamMember.team.id.eq(teamId)))
                .fetch();

        return teamUsers;


    }

    @Override
    public List<SearchUserResponse> getSearchUsers(String query) {

        QUser user = QUser.user;

        List<User> users = queryFactory.select(user)
                .from(user)
                .where(user.name.containsIgnoreCase(query))
                .fetch();

        return users.stream().map(SearchUserResponse::from).toList();
    }

}
