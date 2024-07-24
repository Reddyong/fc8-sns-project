package com.fc8.snsproject.fixture;

import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.user.entity.User;

public class PostEntityFixture {

    public static Post get(Long id, Long userId, String username) {
        User user = User.of(userId, username, "");

        return Post.of(id, user, "title", "body");
    }
}
