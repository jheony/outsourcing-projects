package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.QUser;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.example.outsourcingprojects.common.entity.QTask.task;

@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Task> findOverdueTasks(Long assigneeId, LocalDate now) {
        QTask task = QTask.task;

        return queryFactory
                .selectFrom(task)
                .where(
                        task.assignee.id.eq(assigneeId),
                        task.deletedAt.isNull(),
                        task.dueDate.lt(now.atStartOfDay())
                )
                .orderBy(task.dueDate.asc())
                .fetch();
    }


    public List<Task> findTodayTasks(Long assigneeId, LocalDate today) {
        QTask task = QTask.task;

        return queryFactory
                .selectFrom(task)
                .where(
                        task.assignee.id.eq(assigneeId),
                        task.deletedAt.isNull(),
                        task.dueDate.between(
                                today.atStartOfDay(),
                                today.plusDays(1).atStartOfDay()
                        )
                )
                .orderBy(task.dueDate.asc())
                .fetch();
    }

    public List<Task> findUpcomingTasks(Long assigneeId, LocalDate tomorrow) {
        QTask task = QTask.task;

        return queryFactory
                .selectFrom(task)
                .where(
                        task.assignee.id.eq(assigneeId),
                        task.deletedAt.isNull(),
                        task.dueDate.gt(tomorrow.atStartOfDay())
                )
                .orderBy(task.dueDate.asc())
                .fetch();
    }

    @Override
    public List<Tuple> countTasksByStatus() {
        QTask task = QTask.task;

        return queryFactory
                .select(task.status, task.id.count())
                .from(task)
                .where(task.deletedAt.isNull())
                .groupBy(task.status)
                .orderBy(task.status.asc())
                .fetch();
    }

    @Override
    public Long countOverdueTask() {
        QTask task = QTask.task;

        LocalDateTime now = LocalDateTime.now();

        Long result = queryFactory
                .select(task.id.count())
                .from(task)
                .where(task.dueDate.before(now)
                        .and(task.status.ne(TaskStatusType.DONE.getStatusNum()))
                        .and(task.deletedAt.isNull()))
                .fetchOne();

        return result != null ? result : 0L;
    }

    @Override
    public Long countMyTaskToday(Long userId) {
        QTask task = QTask.task;

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);

        Long result = queryFactory
                .select(task.id.count())
                .from(task)
                .where(task.assignee.id.eq(userId)
                        .and(task.deletedAt.isNull())
                        .and(task.createdAt.between(start, end)))
                .fetchOne();

        return result != null ? result : 0L;
    }

    @Override
    public DailyTaskDTO getDailyTask(Integer before, Long userId) {
        QTask task = QTask.task;

        LocalDate date = LocalDate.now().minusDays(before);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        NumberExpression<Long> completedExpr =
                Expressions.numberTemplate(Long.class,
                        "sum(case when {0} = {1} then 1 else 0 end)",
                        task.status, TaskStatusType.DONE.getStatusNum()
                );

        Tuple result = queryFactory
                .select(
                        task.id.count(),
                        completedExpr
                )
                .from(task)
                .where(task.assignee.id.eq(userId)
                        .and(task.deletedAt.isNull())
                        .and(task.createdAt.between(startOfDay, endOfDay)))
                .fetchOne();

        Long tasks = result != null ? result.get(task.id.count()) : 0L;
        Long completed = result != null ? result.get(completedExpr) : 0L;

        String[] korDays = {"일", "월", "화", "수", "목", "금", "토"};
        int dayIndex = date.getDayOfWeek().getValue();
        Character dayChar = korDays[dayIndex % 7].charAt(0);

        return new DailyTaskDTO(dayChar, tasks, completed, date.toString());
    }

    @Override
    public List<SearchTaskResponse> getSearchTasks(String query) {
        QTask task = QTask.task;

        List<Task> tasks = queryFactory
                .selectFrom(task)
                .where(
                        task.title.containsIgnoreCase(query),
                        task.deletedAt.isNull()
                )
                .orderBy(task.createdAt.desc())
                .limit(100)
                .fetch();

        return tasks.stream().map(SearchTaskResponse::from).toList();
    }

    @Override
    public Page<Task> getAllTaskWithCondition(Long status, String query, Long assigneeId, Pageable pageable) {

        QTask task = QTask.task;
        QUser user = QUser.user;

        List<Task> tasks = queryFactory
                .selectFrom(task)
                .join(task.assignee, user)
                .fetchJoin()
                .where(
                        queryEq(query),
                        statusEq(status),
                        assigneeIdEq(assigneeId),
                        task.deletedAt.isNull()
                )
                .orderBy(task.createdAt.desc())
                .limit(100L)
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(task.count())
                .from(task)
                .where(
                        queryEq(query),
                        statusEq(status),
                        assigneeIdEq(assigneeId),
                        task.deletedAt.isNull()
                );

        return PageableExecutionUtils.getPage(tasks, pageable, countQuery::fetchOne);
    }

    private BooleanExpression queryEq(String query) {
        return query != null ? task.title.containsIgnoreCase(query) : null;
    }

    private BooleanExpression statusEq(Long status) {
        return status != null ? task.status.eq(status) : null;
    }

    private BooleanExpression assigneeIdEq(Long assigneeId) {
        return assigneeId != null ? task.assignee.id.eq(assigneeId) : null;
    }
}
