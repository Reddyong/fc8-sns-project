package com.fc8.snsproject.domain.user.dto;

public record UserJoinResponse(
        Long id,
        String username,
        String role
) {

    public static UserJoinResponse of(Long id, String username, String role) {
        return new UserJoinResponse(id, username, role);
    }

    public static UserJoinResponse from(UserDto userDto) {
        return UserJoinResponse.of(
                userDto.id(),
                userDto.username(),
                userDto.role()
        );
    }
}
