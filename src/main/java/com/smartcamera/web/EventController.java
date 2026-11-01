package com.smartcamera.web;

import com.smartcamera.repository.EventRepository;
import com.smartcamera.security.CurrentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository events;
    private final CurrentUserService current;

    public EventController(EventRepository events, CurrentUserService current) {
        this.events = events;
        this.current = current;
    }

    @GetMapping
    @PreAuthorize("hasRole('RESIDENT')")
    public List<EventResponse> list(Authentication auth) {
        return events.findByOwner_UserId(current.requireCurrentUser(auth).getUserId())
                .stream().map(EventResponse::from).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RESIDENT') and @eventAuthorization.isOwner(#id, authentication)")
    public EventResponse get(@PathVariable("id") Integer id) {
        return events.findById(id).map(EventResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/share")
    @PreAuthorize("hasRole('RESIDENT') and @eventAuthorization.isOwner(#id, authentication)")
    public void share(@PathVariable("id") Integer id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("denyAll()")
    public void delete(@PathVariable("id") Integer id) {}

    @PutMapping("/{id}")
    @PreAuthorize("denyAll()")
    public void update(@PathVariable("id") Integer id) {}
}