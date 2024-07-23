package com.fc8.snsproject.domain.user.dto.response;

public record UserLoginResponse(
        String token
) {
    public static UserLoginResponse of(String token) {
        return new UserLoginResponse(token);
    }
}
