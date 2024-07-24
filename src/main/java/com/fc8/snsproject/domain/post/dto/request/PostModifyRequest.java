package com.fc8.snsproject.domain.post.dto.request;

public record PostModifyRequest(
        String title,
        String body
) {
    public static PostModifyRequest of(String title, String body) {
        return new PostModifyRequest(title, body);
    }
}
