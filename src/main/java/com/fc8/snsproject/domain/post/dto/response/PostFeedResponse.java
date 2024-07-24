package com.fc8.snsproject.domain.post.dto.response;

import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

import java.sql.Timestamp;

public record PostFeedResponse(
        Long id,
        String username,
        String title,
        String body,
        Timestamp registeredAt,
        Timestamp updatedAt
) {
    public static PostFeedResponse of(Long id, String username, String title, String body, Timestamp registeredAt, Timestamp updatedAt) {
        return new PostFeedResponse(id, username, title, body, registeredAt, updatedAt);
    }

    public static PostFeedResponse from(PostDto postDto) {
        return PostFeedResponse.of(
                postDto.id(),
                postDto.user().getUsername(),
                postDto.title(),
                postDto.body(),
                postDto.registeredAt(),
                postDto.updatedAt()
        );
    }
}
