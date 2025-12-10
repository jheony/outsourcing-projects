package com.example.outsourcingprojects.common.entity;


import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponseDto;
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
    // 속
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

    // 생
    public Task(String title, String description, long priority, long status, User assignee, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getPriority() {
        return priority;
    }

    public long getStatus() {
        return status;
    }

    public User getAssignee() {
        return assignee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    // 작업 수정 메서드
    public void update(String title, String description, Long status, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }
}


