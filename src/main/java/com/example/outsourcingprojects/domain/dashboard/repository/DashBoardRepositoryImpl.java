package com.example.outsourcingprojects.domain.dashboard.repository;

import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.QUser;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class DashBoardRepositoryImpl implements DashBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Task> findAllByAssigneeIdAndStatus(Long assigneeId, Long status) {
        QTask task = QTask.task;

        Pageable pageable = Pageable.ofSize(3).withPage(0);

        List<Task> result = queryFactory
                .selectFrom(task)
                .where(task.assignee.id.eq(assigneeId)
                        .and(task.status.eq(status))
                        .and(task.deletedAt.isNull()))
                .orderBy(task.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(task.id.count())
                .from(task)
                .where(task.assignee.id.eq(assigneeId)
                        .and(task.status.eq(status))
                        .and(task.deletedAt.isNull()))
                .fetchOne();

        total = total == null ? 0L : total;

        return new PageImpl<>(result, pageable, total);
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

        Tuple result = queryFactory
                .select(
                        task.id.count(),
                        task.status.when(TaskStatusType.DONE.getStatusNum()).then(1).otherwise(0).sumLong()
                )
                .from(task)
                .where(task.assignee.id.eq(userId)
                        .and(task.deletedAt.isNull())
                        .and(task.createdAt.between(startOfDay, endOfDay)))
                .fetchOne();

        Long tasks = result != null ? result.get(task.id.count()) : 0L;
        Long completed = result != null ? result.get(task.status.when(TaskStatusType.DONE.getStatusNum()).then(1).otherwise(0).sumLong()) : 0L;

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
                .where(task.title.containsIgnoreCase(query))
                .fetch();

        return tasks.stream().map(SearchTaskResponse::from).toList();
    }
}
