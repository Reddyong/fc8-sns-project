package com.fc8.snsproject.domain.user.service;

import com.fc8.snsproject.domain.user.dto.UserJoinResponse;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // TODO : implement
    public UserJoinResponse join(String username, String password) {
        // 회원가입 하려는 username 으로 회원가입한 user 가 존재하는지
        User user = userRepository.findByUsername(username).orElseThrow(SnsApplicationException::new);

        // 회원가입 진행 = user 를 등록
        userRepository.save(new User());

        return UserJoinResponse.of(1L, "");
    }

    public String login(String username, String password) {
        // 회원가입 여부 체크
        User user = userRepository.findByUsername(username).orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (!user.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성

        return "";
    }
}
