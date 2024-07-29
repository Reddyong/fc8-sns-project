package com.fc8.snsproject.domain.post.dto.request;

public record PostCommentRequest(
        String comment
) {
    public static PostCommentRequest of(String comment) {
        return new PostCommentRequest(comment);
    }
}
