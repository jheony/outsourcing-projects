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
    private Customer assigneeId;

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

}