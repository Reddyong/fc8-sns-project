package com.fc8.snsproject.domain.post.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.post.repository.PostRepository;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(String title, String body, String username) {
        // user find
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s Not Founded", username)));

        // post save
        postRepository.save(new Post());
    }
}
