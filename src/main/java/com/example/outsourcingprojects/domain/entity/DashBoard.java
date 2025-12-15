package com.example.outsourcingprojects.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "dashboards")
public class DashBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long totalTasks;

    @Column
    private Long completedTasks;

    @Column
    private Long inProgressTasks;

    @Column
    private Long todoTasks;

    @Column
    private Long overdueTasks;

    @Column
    private Double teamProgress;

    @Column
    private Double completionRate;

    private DashBoard(Long totalTasks, Long completedTasks, Long inProgressTasks, Long todoTasks, Long overdueTasks, Double teamProgress, Double completionRate) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.inProgressTasks = inProgressTasks;
        this.todoTasks = todoTasks;
        this.overdueTasks = overdueTasks;
        this.teamProgress = teamProgress;
        this.completionRate = completionRate;
    }

    public static DashBoard from(Long totalTasks, Long completedTasks, Long inProgressTasks, Long todoTasks, Long overdueTasks, Double teamProgress, Double completionRate) {
        return new DashBoard(totalTasks, completedTasks, inProgressTasks, todoTasks, overdueTasks, teamProgress, completionRate);
    }
}
