package com.fc8.snsproject.domain.post.dto;

import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.user.entity.User;

import java.sql.Timestamp;

public record PostDto(
        Long id,
        User user,
        String title,
        String body,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {

    public static PostDto of(Long id, User user, String title, String body, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new PostDto(id, user, title, body, registeredAt, updatedAt, deletedAt);
    }

    public static PostDto from(Post post) {
        return PostDto.of(
                post.getId(),
                post.getUser(),
                post.getTitle(),
                post.getBody(),
                post.getRegisteredAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }
}
