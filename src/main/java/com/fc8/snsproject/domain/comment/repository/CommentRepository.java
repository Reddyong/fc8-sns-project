package com.fc8.snsproject.domain.comment.repository;

import com.fc8.snsproject.domain.comment.entity.Comment;
import com.fc8.snsproject.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost(Post post, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Comment c SET c.deletedAt = CURRENT TIMESTAMP where c.post = :post")
    void deleteAllByPost(@Param("post") Post post);
}
