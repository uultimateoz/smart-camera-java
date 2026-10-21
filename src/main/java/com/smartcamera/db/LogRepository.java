package com.smartcamera.db;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogRepository
        extends JpaRepository<LogEntry, Integer> {

    List<LogEntry> findByUser_UserIdOrderByCreatedAtDesc(
        Integer userId
    );
}