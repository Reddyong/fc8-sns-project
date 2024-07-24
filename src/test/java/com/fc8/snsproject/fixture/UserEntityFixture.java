package com.fc8.snsproject.fixture;

import com.fc8.snsproject.domain.user.entity.User;

public class UserEntityFixture {

    public static User get(String username, String password) {
        return User.of(username, password);
    }

    public static User get(Long id, String username, String password) {
        return User.of(id, username, password);
    }
}
