package com.smartcamera.db;

import com.smartcamera.repository.EventRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class RetentionService {

    private final EventRepository eventRepository;

    public RetentionService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Scheduled(cron = "0 0 2 * * *") 
    @Transactional
    public void deleteExpiredEvents() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        int deletedCount = eventRepository.deleteExpiredEvents(cutoff);
        System.out.println("Expired events deleted: " + deletedCount);
    }
}
