package com.fc8.snsproject.domain.post.repository;

import com.fc8.snsproject.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
