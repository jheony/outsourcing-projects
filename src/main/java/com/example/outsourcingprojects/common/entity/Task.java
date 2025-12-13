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
    public Task(String title, String description, Long priority, Long status, User assignee, LocalDateTime dueDate) {

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
    }


    // 작업 수정 메서드
    public void update(String title, String description, Long status, long statusNum, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    public void updateStatus(long statusNum) {
        this.status = statusNum;
    }
}


