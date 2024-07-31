package com.fc8.snsproject.consumer;

import com.fc8.snsproject.domain.alarm.service.AlarmService;
import com.fc8.snsproject.domain.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = {"${spring.kafka.topic.alarm}"})
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack) {
        log.info("Consume the event {}", event);
        alarmService.send(event.getAlarmType(), event.getReceiveUserId(), event.getArgs());
        ack.acknowledge();
    }
}
