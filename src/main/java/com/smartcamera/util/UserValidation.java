package com.smartcamera.util;

import com.smartcamera.model.User;
import java.util.regex.Pattern;

public final class UserValidation {

    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private UserValidation() {}

    public static void validateUser(User user) {
        if (user == null 
                || user.getUsername() == null || user.getUsername().isBlank() || user.getUsername().length() > 50
                || user.getEmail() == null || user.getEmail().length() > 100 || !EMAIL_PATTERN.matcher(user.getEmail()).matches()
                || user.getPassword() == null || user.getPassword().length() < 8 || user.getPassword().length() > 72
                || user.getRole() == null 
                || user.getAttempts() < 0 
                || (user.getPhone() != null && user.getPhone().length() > 20)) {
            
            throw new IllegalArgumentException("Invalid input");
        }
    }
}