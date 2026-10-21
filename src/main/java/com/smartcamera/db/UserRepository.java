package com.smartcamera.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("""
        UPDATE User u
        SET u.attempts = :attempts,
            u.lockedUntil = :lockedUntil
        WHERE u.userId = :userId
        """)
    int updateFailedAttempts(
        @Param("userId") Integer userId,
        @Param("attempts") int attempts,
        @Param("lockedUntil") LocalDateTime lockedUntil
    );
}
