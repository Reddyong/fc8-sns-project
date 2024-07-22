package com.fc8.snsproject.domain.user.dto;

public record UserJoinResponse(
        Long id,
        String username
) {

    public static UserJoinResponse of(Long id, String username) {
        return new UserJoinResponse(id, username);
    }
}
