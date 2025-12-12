package com.example.outsourcingprojects.domain.activitylog.repository;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.entity.QActivityLog;
import com.example.outsourcingprojects.common.entity.QUser;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class ActivityLogRepositoryImpl implements ActivityLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ActivityLog> getActivityLogs(Long userId, Pageable pageable) {

        QActivityLog activityLog = QActivityLog.activityLog;
        QUser user = QUser.user;

        List<ActivityLog> activityLogs = queryFactory.selectFrom(activityLog)
                .join(activityLog.user, user).fetchJoin()
                .where(activityLog.user.id.eq(userId))
                .orderBy(activityLog.timestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(activityLog.count())
                .from(activityLog)
                .where(activityLog.user.id.eq(userId));

        return PageableExecutionUtils.getPage(activityLogs, pageable, countQuery::fetchOne);
    }
}