package com.smartcamera.repository;

import com.smartcamera.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByOwner_UserId(Integer userId);
    boolean existsByEventIdAndOwnerUserId(Integer eventId, Integer userId);

    @Modifying
    @Query("DELETE FROM Event e WHERE e.captureTime < :cutoff")
    int deleteExpiredEvents(@Param("cutoff") LocalDateTime cutoff);
}