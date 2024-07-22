package com.fc8.snsproject.fixture;

import com.fc8.snsproject.domain.user.entity.User;

public class UserEntityFixture {

    public static User get(String username, String password) {
        return User.of(username, password);
    }
}
