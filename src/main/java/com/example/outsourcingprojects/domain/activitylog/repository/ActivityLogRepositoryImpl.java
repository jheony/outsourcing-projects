package com.example.outsourcingprojects.domain.activitylog.repository;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.entity.QActivityLog;
import com.example.outsourcingprojects.common.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.outsourcingprojects.common.entity.QActivityLog.activityLog;

@RequiredArgsConstructor
public class ActivityLogRepositoryImpl implements ActivityLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ActivityLog> getMyActivityLogs(Long userId, Pageable pageable) {

        QActivityLog activityLog = QActivityLog.activityLog;
        QUser user = QUser.user;

        List<ActivityLog> activityLogs = queryFactory
                .selectFrom(activityLog)
                .join(activityLog.user, user)
                .fetchJoin()
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

    @Override
    public Page<ActivityLog> getAllActivityLogs(Pageable pageable) {

        QActivityLog activityLog = QActivityLog.activityLog;
        QUser user = QUser.user;

        List<ActivityLog> activityLogs = queryFactory
                .selectFrom(activityLog)
                .join(activityLog.user, user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(activityLog.count())
                .from(activityLog);

        return PageableExecutionUtils.getPage(activityLogs, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<ActivityLog> getAllActivityLogsWithCondition(Long typeNum, Long taskId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

        QActivityLog activityLog = QActivityLog.activityLog;
        QUser user = QUser.user;

        List<ActivityLog> activityLogs = queryFactory
                .selectFrom(activityLog)
                .join(activityLog.user, user)
                .fetchJoin()
                .where(
                        typeEq(typeNum),
                        taskIdEq(taskId),
                        startDateEq(startDate),
                        endTimeEq(endDate)
                )
                .orderBy(activityLog.timestamp.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(activityLog.count())
                .from(activityLog)
                .where(
                        typeEq(typeNum),
                        taskIdEq(taskId),
                        startDateEq(startDate),
                        endTimeEq(endDate)
                );

        return PageableExecutionUtils.getPage(activityLogs, pageable, countQuery::fetchOne);
    }

    private BooleanExpression typeEq(Long typeNum) {
        return typeNum != null ? activityLog.type.eq(typeNum) : null;
    }

    private BooleanExpression taskIdEq(Long taskId) {
        return taskId != null ? activityLog.taskId.eq(taskId) : null;
    }

    private BooleanExpression startDateEq(LocalDateTime startDate) {
        return startDate != null ? activityLog.timestamp.goe(startDate) : null;
    }

    private BooleanExpression endTimeEq(LocalDateTime endTime) {
        return endTime != null ? activityLog.timestamp.loe(endTime) : null;
    }
}