package com.fc8.snsproject.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.post.repository.PostRepository;
import com.fc8.snsproject.domain.post.service.PostService;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import com.fc8.snsproject.fixture.PostEntityFixture;
import com.fc8.snsproject.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @DisplayName(value = "포스트 작성 성공")
    @Test
    void givenTitleAndBodyAndUsername_whenSavingPost_thenSavesPost() {
        // given
        String title = "title";
        String body = "body";
        String username = "test";
        User user = User.of(username, "");

        // when
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(User.of(username, "")));
        when(postRepository.save(any())).thenReturn(Post.of(user, title, body));

        // then
        assertDoesNotThrow(() -> postService.create(title, body, username));

    }

    @DisplayName(value = "포스트 작성 실패 - 요청한 사용자가 존재하지 않는 경우")
    @Test
    void givenTitleAndBodyAndNoneExistingUsername_whenSavingPost_thenSavesPostFailed() {
        // given
        String title = "title";
        String body = "body";
        String username = "hong";
        User user = User.of(username, "");

        // when
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(Post.of(user, title, body));

        // then
        SnsApplicationException snsApplicationException = assertThrows(SnsApplicationException.class,
                () -> postService.create(title, body, username));
        assertEquals(ErrorCode.USER_NOT_FOUND, snsApplicationException.getErrorCode());
    }

    @DisplayName(value = "포스트 수정 성공")
    @Test
    void givenTitleAndBodyAndUsername_whenUpdatingPost_thenUpdatesPost() {
        // given
        String title = "title";
        String body = "body";
        String username = "test";
        Long postId = 1L;
        Post post = PostEntityFixture.get(postId, 1L, username);
        User user = post.getUser();

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // then
        assertDoesNotThrow(() -> postService.update(title, body, username, postId));

    }

    @DisplayName(value = "포스트 수정 실패 - 수정할 포스트가 존재하지 않는 경우")
    @Test
    void givenTitleAndBodyAndNoneExistingPostId_whenUpdatingPost_thenUpdatesPostFailed() {
        // given
        String title = "title";
        String body = "body";
        String username = "hong";
        Long postId = 1L;
        Post post = PostEntityFixture.get(postId, 1L, username);
        User user = post.getUser();

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // then
        SnsApplicationException snsApplicationException = assertThrows(SnsApplicationException.class,
                () -> postService.update(title, body, username, postId));
        assertEquals(ErrorCode.POST_NOT_FOUND, snsApplicationException.getErrorCode());
    }

    @DisplayName(value = "포스트 수정 실패 - 수정할 포스트의 작성자가 아닌 경우")
    @Test
    void givenTitleAndBodyAndDifferentUser_whenUpdatingPost_thenUpdatesPostFailed() {
        // given
        String title = "title";
        String body = "body";
        String username = "hong";
        Long postId = 1L;
        Post post = PostEntityFixture.get(postId, 1L, username);
        User wrongUser = UserEntityFixture.get(2L, "kim", "");

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(wrongUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // then
        SnsApplicationException snsApplicationException = assertThrows(SnsApplicationException.class,
                () -> postService.update(title, body, username, postId));
        assertEquals(ErrorCode.INVALID_PERMISSION, snsApplicationException.getErrorCode());
    }
}
