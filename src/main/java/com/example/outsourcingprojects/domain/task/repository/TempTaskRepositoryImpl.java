package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.QUser;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TempTaskRepositoryImpl implements TempTaskRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Task> findAllByAssigneeIdAndStatus(Long assigneeId, Long status) {
        QTask task = QTask.task;
        QUser user = QUser.user;

        Pageable pageable = Pageable.ofSize(3).withPage(0);

        List<Task> result = queryFactory
                .selectFrom(task)
                .join(task.assignee, user)
                .where(task.assignee.id.eq(assigneeId)
                        .and(task.status.eq(status))
                        .and(task.deletedAt.isNull()))
                .orderBy(task.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(task.count())
                .from(task)
                .join(task.assignee, user)
                .where(
                        task.assignee.id.eq(assigneeId)
                                .and(task.status.eq(status))
                                .and(task.deletedAt.isNull())
                )
                .fetchOne();

        total = total == null ? 0L : total;

        return new PageImpl<>(result, pageable, total);
    }

//    @Override
//    public Double getTeamProgressByUserId(Long userID) {
//        QUser user = QUser.user;
//        QTask task = QTask.task;
//        QTeamMember teamMember = QTeamMember.teamMember;
//
//        Long myTeamId = queryFactory.select(teamMember.team.id)
//                .from(teamMember)
//                .where(teamMember.user.id.eq(userID))
//                .fetchOne();
//
//        if (myTeamId == null) {
//            return 0.0;
//        }
//
//        List<Long> teamUserIds = queryFactory
//                .select(teamMember.user.id)
//                .from(teamMember)
//                .where(teamMember.team.id.eq(myTeamId))
//                .fetch();
//
//        if (teamUserIds == null || teamUserIds.isEmpty()) {
//            return 0.0;
//        }
//
//        Long totalTasks = queryFactory
//                .select(task.id.count())
//                .from(task)
//                .where(task.assignee.id.in(teamUserIds))
//                .fetchOne();
//
//        if (totalTasks == null || totalTasks == 0) {
//            return 0.0;
//        }
//
//        Long completedTasks = queryFactory
//                .select(task.id.count())
//                .from(task)
//                .where(task.assignee.id.in(teamUserIds).and(task.status.eq(TaskStatusType.DONE.getStatusNum())))
//                .fetchOne();
//
//
//        return (completedTasks.doubleValue() / totalTasks.doubleValue()) * 100.0;
//    }

    @Override
    public List<Tuple> countTasksByStatus() {

        QTask task = QTask.task;

        return queryFactory.select(task.status, task.id.count())
                .from(task)
                .where(task.deletedAt.isNull())
                .groupBy(task.status)
                .orderBy(task.status.asc())
                .fetch();
    }

    @Override
    public Long countOverdueTask() {

        QTask task = QTask.task;

        LocalDateTime currentTime = LocalDateTime.now();

        Long result = queryFactory.select(task.id.count())
                .from(task)
                .where(task.dueDate.before(currentTime)
                        .and(task.status.ne(TaskStatusType.DONE.getStatusNum()))
                        .and(task.deletedAt.isNull()))
                .fetchOne();

        return result != null ? result : 0;
    }

    @Override
    public Long countMyTaskToday(Long userId) {

        QTask task = QTask.task;

        Long result = queryFactory.select(task.id.count())
                .from(task)
                .where(task.deletedAt.isNull().and(task.assignee.id.eq(userId)))
                .fetchOne();

        return result != null ? result : 0L;
    }

}