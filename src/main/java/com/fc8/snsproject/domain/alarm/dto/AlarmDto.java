package com.fc8.snsproject.domain.alarm.dto;

import com.fc8.snsproject.domain.alarm.entity.Alarm;
import com.fc8.snsproject.domain.alarm.entity.AlarmArgs;
import com.fc8.snsproject.domain.alarm.entity.enums.AlarmType;
import com.fc8.snsproject.domain.user.dto.UserDto;
import com.fc8.snsproject.domain.user.entity.User;

import java.sql.Timestamp;

public record AlarmDto(
        Long id,
        AlarmType alarmType,
        AlarmArgs alarmArgs,
        Timestamp registeredAt,
        Timestamp updatedAt,
        Timestamp deletedAt
) {

    public static AlarmDto of(Long id, AlarmType alarmType, AlarmArgs alarmArgs, Timestamp registeredAt, Timestamp updatedAt, Timestamp deletedAt) {
        return new AlarmDto(id, alarmType, alarmArgs, registeredAt, updatedAt, deletedAt);
    }

    public static AlarmDto from(Alarm alarm) {
        return AlarmDto.of(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getRegisteredAt(),
                alarm.getUpdatedAt(),
                alarm.getDeletedAt()
        );
    }
}
