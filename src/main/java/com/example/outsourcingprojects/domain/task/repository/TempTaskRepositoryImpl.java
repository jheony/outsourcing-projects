package com.example.outsourcingprojects.domain.task.repository;

import com.example.outsourcingprojects.common.entity.QTask;
import com.example.outsourcingprojects.common.entity.QUser;
import com.example.outsourcingprojects.common.entity.Task;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .where(task.assignee.id.eq(assigneeId),
                        task.status.eq(status)
                )
                .orderBy(task.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(task.count())
                .from(task)
                .join(task.assignee, user)
                .where(
                        task.assignee.id.eq(assigneeId),
                        task.status.eq(status)
                )
                .fetchOne();

        total = total == null ? 0L : total;

        return new PageImpl<>(result, pageable, total);
    }
}