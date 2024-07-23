package com.fc8.snsproject.domain.user.dto;

import com.fc8.snsproject.domain.user.entity.User;

import java.sql.Timestamp;

public record UserDto(
        Long id,
        String username,
        String password,
        String role,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {
    public static UserDto of(Long id, String username, String password, String role, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new UserDto(id, username, password, role, registeredAt, updatedAt, deletedAt);
    }

    public static UserDto of(String username, String password, String role) {
        return new UserDto(null, username, password, role, null, null, null);
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole().name(), user.getRegisteredAt(), user.getUpdatedAt(), user.getDeletedAt());
    }

    public User toEntity() {
        return User.of(
                username,
                password
        );
    }
}
