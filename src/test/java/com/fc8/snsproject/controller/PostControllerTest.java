package com.fc8.snsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc8.snsproject.domain.post.dto.request.PostCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @DisplayName(value = "포스트 작성")
    @WithMockUser
    @Test
    void givenPostTitleAndBody_whenSavingPost_thenReturnsOKResponse() throws Exception {
        // given
        String title = "title";
        String body = "body";

        // when, then
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

}
