package com.fc8.snsproject.domain.user.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.alarm.dto.AlarmDto;
import com.fc8.snsproject.domain.alarm.entity.Alarm;
import com.fc8.snsproject.domain.alarm.repository.AlarmRepository;
import com.fc8.snsproject.domain.user.dto.UserDto;
import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.repository.UserRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import com.fc8.snsproject.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

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
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", username)));

        // 비밀번호 체크
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        return JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);
    }

    public UserDto loadByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDto::from).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", username))
        );
    }

    public Page<AlarmDto> alarmList(String username, Pageable pageable) {
        // 회원가입 여부 체크
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s is not founded", username)));

        Page<Alarm> alarmPage = alarmRepository.findAllByUser(user, pageable);

        return alarmPage.map(AlarmDto::from);
    }
}
