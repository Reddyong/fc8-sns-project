package com.fc8.snsproject.domain.alarm.entity;

import com.fc8.snsproject.domain.alarm.entity.enums.AlarmType;
import com.fc8.snsproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.Instant;

// TODO : @Where 을 사용하는 방법을 현재 hibernate 방법에 맞게 고쳐보기
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE alarms SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@Table(indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
}, name = "alarms"
)
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;      // 알람을 받은 사람

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private AlarmArgs args;     // 알람에 들어가 있어야 하는 정보들

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Alarm(User user, AlarmType alarmType, AlarmArgs args) {
        this.user = user;
        this.alarmType = alarmType;
        this.args = args;
    }

    public static Alarm of(User user, AlarmType alarmType, AlarmArgs alarmArgs) {
        return new Alarm(user, alarmType, alarmArgs);
    }

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
