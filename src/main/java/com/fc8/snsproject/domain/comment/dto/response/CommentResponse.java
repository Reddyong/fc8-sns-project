package com.fc8.snsproject.domain.comment.dto.response;

import com.fc8.snsproject.domain.comment.dto.CommentDto;

import java.sql.Timestamp;

public record CommentResponse(
        Long id,
        String username,
        Long postId,
        String content,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {
    public static CommentResponse of(Long id, String username, Long postId, String content, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new CommentResponse(id, username, postId, content, registeredAt, updatedAt, deletedAt);
    }

    public static CommentResponse from(CommentDto commentDto) {
        return CommentResponse.of(
                commentDto.id(),
                commentDto.username(),
                commentDto.postId(),
                commentDto.content(),
                commentDto.registeredAt(),
                commentDto.updatedAt(),
                commentDto.deletedAt()
        );
    }
}
