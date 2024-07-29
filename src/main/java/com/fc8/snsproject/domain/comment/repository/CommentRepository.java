package com.fc8.snsproject.domain.comment.repository;

import com.fc8.snsproject.domain.comment.entity.Comment;
import com.fc8.snsproject.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPost(Post post, Pageable pageable);
}
