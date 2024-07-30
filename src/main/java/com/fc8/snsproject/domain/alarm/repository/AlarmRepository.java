package com.fc8.snsproject.domain.alarm.repository;

import com.fc8.snsproject.domain.alarm.entity.Alarm;
import com.fc8.snsproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByUserId(Long userId, Pageable pageable);
}