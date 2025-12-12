package com.example.outsourcingprojects.domain.comment.repository;

import com.example.outsourcingprojects.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);
}
