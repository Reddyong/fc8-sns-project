package com.fc8.snsproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fc8.snsproject.domain.user.dto.UserJoinRequest;
import com.fc8.snsproject.domain.user.dto.UserJoinResponse;
import com.fc8.snsproject.domain.user.dto.UserLoginRequest;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.service.UserService;
import com.fc8.snsproject.exception.SnsApplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName(value = "회원 컨트롤러 테스트")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName(value = "회원가입 성공")
    @Test
    void givenUserJoinRequestInfo_whenJoining_thenReturnsSuccessResponse() throws Exception {
        // given
        String username = "Hong";
        String password = "1234";

        // when, then
        when(userService.join(username, password)).thenReturn(mock(UserJoinResponse.class));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserJoinRequest.of(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName(value = "회원가입 실패 - 중복된 ID 로 가입하는 경우")
    @Test
    void givenDuplicatedID_whenJoining_thenReturnsBadRequestResponse() throws Exception {
        // given
        String username = "Hong";
        String password = "1234";

        // when, then
        when(userService.join(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserJoinRequest.of(username, password)))
                ).andDo(print())
                .andExpect(status().isConflict());

    }

    @DisplayName(value = "로그인 성공")
    @Test
    void givenLoginRequestInfo_whenLogin_thenReturnsSuccessLoginResponse() throws Exception {
        // given
        String username = "hong";
        String password = "1234";
        String token = "token";

        // when, then
        when(userService.login(username, password)).thenReturn(token);

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName(value = "로그인 실패 - 없는 ID 입력한 경우")
    @Test
    void givenWrongUserName_whenLogin_thenReturnsFailedResponse() throws Exception {
        // given
        String username = "hong";
        String password = "1234";

        // when, then
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName(value = "로그인 실패 - 틀린 password 입력한 경우")
    @Test
    void givenWrongPassword_whenLogin_thenReturnsFailedResponse() throws Exception {
        // given
        String username = "hong";
        String password = "1111";

        // when, then
        when(userService.login(username, password)).thenThrow(new SnsApplicationException());

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(UserLoginRequest.of(username, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}