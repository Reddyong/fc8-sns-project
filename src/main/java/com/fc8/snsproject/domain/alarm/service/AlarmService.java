package com.fc8.snsproject.domain.alarm.service;

import com.fc8.snsproject.common.ErrorCode;
import com.fc8.snsproject.domain.alarm.repository.EmitterRepository;
import com.fc8.snsproject.exception.SnsApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private static final String ALARM_NAME = "alarm";

    private final EmitterRepository emitterRepository;

    public void send(Long alarmId, Long userId) {
        emitterRepository.get(userId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().id(alarmId.toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(userId);
                throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No Emitter Founded"));
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);

        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException e) {
            throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }
}
