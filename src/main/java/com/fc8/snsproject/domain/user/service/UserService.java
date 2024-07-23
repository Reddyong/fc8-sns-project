package com.fc8.snsproject.domain.user.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.user.dto.UserDto;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserDto join(String username, String password) {
        // 회원가입 하려는 username 으로 회원가입한 user 가 존재하는지
        userRepository.findByUsername(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        String encodedPassword = passwordEncoder.encode(password);

        // 회원가입 진행 = user 를 등록
        User savedUser = userRepository.save(User.of(username, encodedPassword));

        return UserDto.from(savedUser);
    }

    public String login(String username, String password) {
        // 회원가입 여부 체크
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, ""));

        // 비밀번호 체크
        if (!user.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, "");
        }

        // 토큰 생성

        return "";
    }
}
