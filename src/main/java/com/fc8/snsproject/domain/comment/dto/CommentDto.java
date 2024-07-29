package com.fc8.snsproject.domain.comment.dto;

import com.fc8.snsproject.domain.comment.entity.Comment;

import java.sql.Timestamp;

public record CommentDto(
        Long id,
        String username,
        Long postId,
        String content,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {
    public static CommentDto of(Long id, String username, Long postId, String content, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new CommentDto(id, username, postId, content, registeredAt, updatedAt, deletedAt);
    }

    public static CommentDto from(Comment comment) {
        return CommentDto.of(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getRegisteredAt(),
                comment.getUpdatedAt(),
                comment.getDeletedAt()
        );
    }
}
