package com.fc8.snsproject.domain.user.dto;

public record UserLoginRequest(
        String username,
        String password
) {

    public static UserLoginRequest of(String username, String password) {
        return new UserLoginRequest(username, password);
    }
}
