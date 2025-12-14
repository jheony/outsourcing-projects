package com.example.outsourcingprojects.domain.comment.repository;

import com.example.outsourcingprojects.common.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

    Page<Comment> findAllByTaskIdAndCommentIsNullAndDeletedAtIsNull(Long taskId, Pageable pageable);

    List<Comment> findAllByTaskIdAndComment_IdInAndDeletedAtIsNull(Long taskId, List<Long> parentIds, Sort sort);

}
