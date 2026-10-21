package com.smartcamera.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository
        extends JpaRepository<Event, Integer> {

    List<Event> findByOwner_UserId(Integer userId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM events
        WHERE capture_time < NOW() - INTERVAL 30 DAY
        """, nativeQuery = true)
    int deleteExpiredEvents();
}