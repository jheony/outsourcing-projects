package com.example.outsourcingprojects.domain.comment.repository;

import com.example.outsourcingprojects.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

//    Boolean<Long> findByIdIsNotAndDeletedAtIsNotNull(Long);

    List<Comment> findAllByTaskIdAndCommentIsNotNull(Long taskId);

    Page<Comment> findAllByTaskIdAndCommentIsNull(Long taskId, Pageable pageable);

}
