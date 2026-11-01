package com.smartcamera.security;

import com.smartcamera.model.User;
import com.smartcamera.model.Role;
import com.smartcamera.repository.EventRepository; // Updated package
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("eventAuthorization")
public class EventAuthorizationService {

    private final EventRepository events;
    private final CurrentUserService current;

    public EventAuthorizationService(EventRepository events, CurrentUserService current) {
        this.events = events;
        this.current = current;
    }

    public boolean isOwner(Integer eventId, Authentication authentication) {
        if (eventId == null || authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        User user = current.requireCurrentUser(authentication);
        return user.getRole() == Role.RESIDENT && events.existsByEventIdAndOwnerUserId(eventId, user.getUserId());
    }
}