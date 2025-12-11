package com.example.outsourcingprojects.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "activities")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User user;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(nullable = false)
    private Long type;

    @Column(nullable = false)
    private String description;

    @CreatedDate
    private LocalDateTime timestamp;
}
