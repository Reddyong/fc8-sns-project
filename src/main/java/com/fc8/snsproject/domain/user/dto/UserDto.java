package com.fc8.snsproject.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fc8.snsproject.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(
        Long id,
        String username,
        String password,
        String role,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) implements UserDetails {
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

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.deletedAt == null;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
