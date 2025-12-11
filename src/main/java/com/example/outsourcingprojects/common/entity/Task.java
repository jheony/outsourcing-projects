package com.example.outsourcingprojects.common.entity;

import com.example.outsourcingprojects.common.model.PriorityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
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

    @Enumerated(EnumType.STRING)
    @Column
    private PriorityType priority;

    @Enumerated(EnumType.STRING)
    @Column
    private TaskStatusType status;

    @Column
    private LocalDateTime dueDate;

    // 생
    public Task(String title, String description, PriorityType priority, TaskStatusType status, User assignee, LocalDateTime dueDate) {
        //필요없어요 Why? 상단의 @NoArgsConstructor를 통해서 이 생성자의 역할을 대신 하고 있습니다. ok
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

    public PriorityType getPriority() {
        return priority;
    }

    public TaskStatusType getStatus() {
        return status;
    }

    public User getAssignee() {
        return assignee;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    // 작업 수정 메서드
    public void update(String title, String description, TaskStatusType status, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }
}


