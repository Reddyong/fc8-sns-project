package com.fc8.snsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.post.dto.PostDto;
import com.fc8.snsproject.domain.post.dto.request.PostCreateRequest;
import com.fc8.snsproject.domain.post.dto.request.PostModifyRequest;
import com.fc8.snsproject.domain.post.entity.Post;
import com.fc8.snsproject.domain.post.service.PostService;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.exception.SnsApplicationException;
import com.fc8.snsproject.fixture.PostEntityFixture;
import com.fc8.snsproject.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "포스트 컨트롤러 테스트")
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @DisplayName(value = "포스트 작성")
    @WithMockUser
    @Test
    void givenPostTitleAndBody_whenSavingPost_thenReturnsOKResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        when(postService.create(eq(title), eq(body), anyString())).thenReturn(mock(PostDto.class));

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostCreateRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "포스트 작성 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenPostTitleAndBody_whenSavingPost_thenReturnsUnauthorizedResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostCreateRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "포스트 수정")
    @WithMockUser
    @Test
    void givenPostTitleAndBody_whenUpdatingPost_thenReturnsOKResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        when(postService.update(eq(title), eq(body), anyString(), anyLong())).thenReturn(mock(PostDto.class));

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostModifyRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "포스트 수정 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenPostTitleAndBody_whenUpdatingPost_thenReturnsUnauthorizedResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostModifyRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "포스트 수정 실패 - 로그인 하였으나, 수정자가 작성자가 아닌경우")
    @WithMockUser
    @Test
    void givenPostTitleAndBody_whenUpdatingPostButNotCorrectWriter_thenReturnsUnauthorizedResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        when(postService.update(eq(title), eq(body), anyString(), anyLong())).thenThrow(new SnsApplicationException(ErrorCode.INVALID_PERMISSION));

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostModifyRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "포스트 수정 실패 - 수정하려는 글이 없는 경우")
    @WithMockUser
    @Test
    void givenPostTitleAndBody_whenUpdatingPostButNoneExistingPost_thenReturnsNotFoundResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
        when(postService.update(eq(title), eq(body), anyString(), anyLong())).thenThrow(new SnsApplicationException(ErrorCode.POST_NOT_FOUND));

        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(PostModifyRequest.of(title, body)))
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

    @DisplayName(value = "포스트 삭제 성공")
    @WithMockUser
    @Test
    void givenPostId_whenDeletingPost_thenReturnsOKResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(delete("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "포스트 삭제 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenPostId_whenDeletingPostWithNoLogin_thenReturnsUnAuthorizedResponse() throws Exception {
        // given

        // when, then
        mockMvc.perform(delete("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "포스트 삭제 실패 - 로그인한 사용자와 삭제할 포스트의 작성자가 다른 경우")
    @WithMockUser
    @Test
    void givenPostIdAndDifferentUser_whenDeletingPost_thenReturnsUnAuthorizedResponse() throws Exception {
        // given

        // when, then
        doThrow(new SnsApplicationException(ErrorCode.INVALID_PERMISSION)).when(postService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "포스트 삭제 실패 - 삭제할 포스트가 존재하지 않는 경우")
    @WithMockUser
    @Test
    void givenNoneExistingPostId_whenDeletingPost_thenReturnsNotFoundResponse() throws Exception {
        // given

        // when, then
        doThrow(new SnsApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

    @DisplayName(value = "포스트 전체 조회 성공")
    @WithMockUser
    @Test
    void givenNothing_whenGettingAllPosts_thenReturnsOkResponse() throws Exception {
        // given

        // when
        when(postService.findAll(any())).thenReturn(Page.empty());

        // then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "포스트 전체 조회 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenNoneLoginUser_whenGettingAllPosts_thenReturnsUnAuthorizedResponse() throws Exception {
        // given

        // when
        when(postService.findAll(any())).thenReturn(Page.empty());

        // then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "내 피드 목록 조회 성공")
    @WithMockUser
    @Test
    void givenNothing_whenGettingMyPosts_thenReturnsOkResponse() throws Exception {
        // given

        // when
        when(postService.findAllMyPosts(any(), anyString())).thenReturn(Page.empty());

        // then
        mockMvc.perform(get("/api/v1/posts/my")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "내 피드 목록 조회 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenNothing_whenFindingMyPosts_thenReturnsUnauthorizedResponse() throws Exception {
        // given

        // when
        when(postService.findAllMyPosts(any(), anyString())).thenReturn(Page.empty());

        // then
        mockMvc.perform(get("/api/v1/posts/my")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "좋아요 기능 성공")
    @WithMockUser
    @Test
    void givenNothing_whenLikingPosts_thenReturnsOkResponse() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "좋아요 기능 실패 - 로그인 하지 않은 경우")
    @WithAnonymousUser
    @Test
    void givenNoting_whenLikingPostsWithNoLogin_thenReturnsUnAuthorizedResponse() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @DisplayName(value = "좋아요 기능 실패 - 좋아요 누를 게시물이 존재하지 않는 경우")
    @WithMockUser
    @Test
    void given_when_then() throws Exception {
        // given

        // when
        doThrow(new SnsApplicationException(ErrorCode.POST_NOT_FOUND)).when(postService).like(anyLong(), anyString());

        // then
        mockMvc.perform(post("/api/v1/posts/1/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());

    }

}
