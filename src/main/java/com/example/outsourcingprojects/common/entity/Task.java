package com.example.outsourcingprojects.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(length = 100)
    @NotNull
    private String title;

    @Column
    private String description;

    @Column
    private Long priority;

    @Column
    private Long status;

    @Column
    private LocalDateTime dueDate;

    public Task(String title, String description, Long priority, Long status, User assignee, LocalDateTime dueDate) {

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
    }

    public void update(String title, String description, Long priorityNum, Long statusNum, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priorityNum;
        this.status = statusNum;
        this.dueDate = dueDate;
    }

    public void updateStatus(Long statusNum) {
        this.status = statusNum;
    }
}