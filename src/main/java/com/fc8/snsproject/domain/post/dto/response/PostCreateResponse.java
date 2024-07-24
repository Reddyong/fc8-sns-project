package com.fc8.snsproject.domain.post.dto.response;

import com.fc8.snsproject.domain.post.dto.PostDto;

public record PostCreateResponse(
        Long id,
        String title,
        String body
) {
    public static PostCreateResponse of(Long id, String title, String body) {
        return new PostCreateResponse(id, title, body);
    }

    public static PostCreateResponse from(PostDto postDto) {
        return PostCreateResponse.of(
                postDto.id(),
                postDto.title(),
                postDto.body()
        );
    }
}
