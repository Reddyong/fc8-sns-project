package com.fc8.snsproject.domain.post.dto.response;

import com.fc8.snsproject.domain.post.dto.PostDto;

public record PostModifyResponse(
        Long id,
        String title,
        String body
) {
    public static PostModifyResponse of(Long id, String title, String body) {
        return new PostModifyResponse(id, title, body);
    }

    public static PostModifyResponse from(PostDto postDto) {
        return PostModifyResponse.of(
                postDto.id(),
                postDto.title(),
                postDto.body()
        );
    }
}
