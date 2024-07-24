package com.fc8.snsproject.domain.post.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.post.dto.PostDto;
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
    public PostDto create(String title, String body, String username) {
        // user find
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s Not Founded", username)));

        // post save
        Post savedPost = postRepository.save(Post.of(user, title, body));

        return PostDto.from(savedPost);
    }

    @Transactional
    public PostDto update(String title, String body, String username, Long postId) {
        // user find
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s Not Founded", username)));

        // post exist
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("Post %s Not Founded", postId))
        );

        // post permission
        if (post.getUser() != user) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission to Post %s", username, postId));
        }

        post.setTitle(title);
        post.setBody(body);

        return PostDto.from(post);
    }

    @Transactional
    public void delete(Long postId, String username) {
        // user find
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s Not Founded", username)));

        // post exist
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("Post %s Not Founded", postId))
        );

        // post permission
        if (!post.getUser().getId().equals(user.getId())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission to Post %s", username, postId));
        }

        postRepository.deleteById(postId);
    }
}
