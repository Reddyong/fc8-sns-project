package com.fc8.snsproject.domain.user.dto.request;

public record UserLoginRequest(
        String name,
        String password
) {

    public static UserLoginRequest of(String username, String password) {
        return new UserLoginRequest(username, password);
    }
}
