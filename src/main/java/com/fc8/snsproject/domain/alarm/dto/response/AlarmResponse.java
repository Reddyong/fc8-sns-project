package com.fc8.snsproject.domain.alarm.dto.response;

import com.fc8.snsproject.domain.alarm.dto.AlarmDto;
import com.fc8.snsproject.domain.alarm.entity.AlarmArgs;
import com.fc8.snsproject.domain.alarm.entity.enums.AlarmType;
import com.fc8.snsproject.domain.user.dto.response.UserJoinResponse;
import com.fc8.snsproject.domain.user.entity.User;

import java.sql.Timestamp;

public record AlarmResponse(
        Long id,
        AlarmType alarmType,
        AlarmArgs alarmArgs,
        String text,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {
    public static AlarmResponse of(Long id, AlarmType alarmType, AlarmArgs alarmArgs, String text, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new AlarmResponse(id, alarmType, alarmArgs, text, registeredAt, updatedAt, deletedAt);
    }

    public static AlarmResponse from(AlarmDto alarmDto) {
        return AlarmResponse.of(
                alarmDto.id(),
                alarmDto.alarmType(),
                alarmDto.alarmArgs(),
                alarmDto.alarmType().getAlarmText(),
                alarmDto.registeredAt(),
                alarmDto.updatedAt(),
                alarmDto.deletedAt()
        );
    }
}
