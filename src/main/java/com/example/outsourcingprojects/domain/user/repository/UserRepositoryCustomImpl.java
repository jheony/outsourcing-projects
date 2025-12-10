package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.common.entity.QTeamMember;
import com.example.outsourcingprojects.common.entity.QUser;
import com.example.outsourcingprojects.common.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> getUsersByTeam(Long teamId) {

        QTeamMember teamMember = QTeamMember.teamMember;
        QUser user = QUser.user;
        List<User> teamUsers = jpaQueryFactory.select(user)
                .from(teamMember)
                .join(teamMember.user, user)
                .where(user.deletedAt.isNull().and(teamMember.deletedAt.isNull()).and(teamMember.team.id.eq(teamId)))
                .fetch();

        return teamUsers;


    }

}
