package com.fc8.snsproject.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.post.repository.PostRepository;
import com.fc8.snsproject.domain.post.service.PostService;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        String username = "hong";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(User.of(username, "")));
        when(postRepository.save(any())).thenReturn(any(Post.class));

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

        // when
        when(userRepository.findByUsername(username)).thenThrow(SnsApplicationException.class);

        // then
        SnsApplicationException snsApplicationException = assertThrows(SnsApplicationException.class,
                () -> postService.create(title, body, username));
        assertEquals(ErrorCode.USER_NOT_FOUND, snsApplicationException.getErrorCode());
    }
}
