package com.fc8.snsproject.domain.user.dto;

import com.fc8.snsproject.domain.user.entity.User;

public record UserDto(
        Long id,
        String username,
        String password
) {
    public static UserDto of(Long id, String username, String password) {
        return new UserDto(id, username, password);
    }

    public static UserDto of(String username, String password) {
        return new UserDto(null, username, password);
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword());
    }

    public User toEntity() {
        return User.of(
                username,
                password
        );
    }
}
