package com.fc8.snsproject.domain.like.repository;

import com.fc8.snsproject.domain.like.entity.Like;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);

    List<Like> findAllByPost(Post post);

    Integer countByPostId(Long postId);

    @Query(value = "SELECT COUNT(*) FROM Like l WHERE l.post = :post")
    Integer countByPost(@Param(value = "post") Post post);
}