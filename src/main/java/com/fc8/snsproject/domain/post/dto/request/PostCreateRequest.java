package com.fc8.snsproject.domain.post.dto.request;

public record PostCreateRequest(
        String title,
        String body
) {
    public static PostCreateRequest of(String title, String body) {
        return new PostCreateRequest(title, body);
    }
}
