package com.fc8.snsproject.domain.post.repository;

import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser(Pageable pageable, User user);
}
