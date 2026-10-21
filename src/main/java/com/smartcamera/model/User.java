package com.smartcamera.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "failed_attempts", nullable = false)
    private int attempts = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    public User(){}

    public User(Integer userId, String username, String email, String password, String role, int attempts, LocalDateTime lockedUntil) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = Role.valueOf(role);
        this.attempts = attempts;
        this.lockedUntil = lockedUntil;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public int getAttempts() {
        return attempts;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }
}