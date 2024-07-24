package com.fc8.snsproject.domain.post.dto;

import com.fc8.snsproject.domain.post.entity.Post;

import java.sql.Timestamp;

public record PostDto(
        Long id,
        Long userId,
        String title,
        String body,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {

    public static PostDto of(Long id, Long userId, String title, String body, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new PostDto(id, userId, title, body, registeredAt, updatedAt, deletedAt);
    }

    public static PostDto from(Post post) {
        return PostDto.of(
                post.getId(),
                post.getUser().getId(),
                post.getTitle(),
                post.getBody(),
                post.getRegisteredAt(),
                post.getUpdatedAt(),
                post.getDeletedAt()
        );
    }
}
