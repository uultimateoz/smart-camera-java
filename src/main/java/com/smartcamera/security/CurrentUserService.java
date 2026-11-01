package com.smartcamera.security;

import com.smartcamera.model.User;
import com.smartcamera.repository.UserRepository; // Updated package
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository users;

    public CurrentUserService(UserRepository users) {
        this.users = users;
    }

    public User requireCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Access denied");
        }

        try {
            Integer userId = Integer.valueOf(auth.getName());
            return users.findById(userId).orElseThrow(() -> new AccessDeniedException("Access denied"));
        } catch (NumberFormatException e) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
