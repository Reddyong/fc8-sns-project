package com.fc8.snsproject.domain.user.dto.request;

public record UserJoinRequest(
        String username,
        String password
) {

    public static UserJoinRequest of(String username, String password) {
        return new UserJoinRequest(username, password);
    }
}
