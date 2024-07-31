package com.fc8.snsproject.domain.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Long fromUserId;

    // 알람이 발생 된 게시물
    private Long targetId;
}
