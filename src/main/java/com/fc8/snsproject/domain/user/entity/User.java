package com.fc8.snsproject.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User of(String username, String password) {
        return new User(username, password);
    }
}
