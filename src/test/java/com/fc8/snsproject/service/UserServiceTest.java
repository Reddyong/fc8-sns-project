package com.fc8.snsproject.service;

import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.domain.user.service.UserService;
import com.fc8.snsproject.exception.SnsApplicationException;
import com.fc8.snsproject.fixture.UserEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName(value = "회원 서비스 테스트")
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName(value = "회원가입 성공")
    @Test
    void givenUsernameAndPassword_whenRegistering_thenRegistersUser() {
        // given
        String username = "hong";
        String password = "1234";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntityFixture.get(username, password))));

        // then
        assertDoesNotThrow(() -> userService.join(username, password));

    }

    @DisplayName(value = "회원가입 실패 - 중복된 ID로 가입하는 경우")
    @Test
    void givenDuplicatedUsernameAndPassword_whenRegistering_thenRegistersFailed() {
        // given
        String username = "hong";
        String password = "1234";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mock(UserEntityFixture.get(username, password))));
        when(passwordEncoder.encode(password)).thenReturn("encrypt_password");
        when(userRepository.save(any())).thenReturn(Optional.of(mock(UserEntityFixture.get(username, password))));

        // then
        assertThrows(SnsApplicationException.class, () -> userService.join(username, password));

    }

    @DisplayName(value = "로그인 성공")
    @Test
    void givenUsernameAndPassword_whenLogin_thenLoginSuccess() {
        // given
        String username = "hong";
        String password = "1234";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(UserEntityFixture.get(username, password)));
        when(userRepository.save(any())).thenReturn(Optional.of(mock(User.class)));

        // then
        assertDoesNotThrow(() -> userService.login(username, password));

    }

    @DisplayName(value = "로그인 실패 - 없는 ID 입력한 경우")
    @Test
    void givenNoneExistUsernameAndPassword_whenLogin_thenLoginFailed() {
        // given
        String username = "hong";
        String password = "1234";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // then
        assertThrows(SnsApplicationException.class, () -> userService.login(username, password));

    }

    @DisplayName(value = "로그인 실패 - 틀린 password 입력한 경우")
    @Test
    void givenWrongPasswordAndUsername_whenLogin_thenLoginFailed() {
        // given
        String username = "hong";
        String password = "1234";
        String wrongPassword = "1111";

        // when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(UserEntityFixture.get(username, password)));

        // then
        assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongPassword));

    }
}
