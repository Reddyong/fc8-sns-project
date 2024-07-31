package com.fc8.snsproject.domain.event;

import com.fc8.snsproject.domain.alarm.entity.AlarmArgs;
import com.fc8.snsproject.domain.alarm.entity.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {

    private Long receiveUserId;
    private AlarmType alarmType;
    private AlarmArgs args;
}
