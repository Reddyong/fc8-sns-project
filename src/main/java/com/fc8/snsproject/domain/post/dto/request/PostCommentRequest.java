package com.fc8.snsproject.domain.post.dto.request;

public record PostCommentRequest(
        String content
) {
    public static PostCommentRequest of(String content) {
        return new PostCommentRequest(content);
    }
}
