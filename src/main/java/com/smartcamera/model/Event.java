package com.smartcamera.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "camera_id", nullable = false, length = 50)
    private String cameraId;

    @Column(name = "capture_time", insertable = false, updatable = false)
    private LocalDateTime captureTime;

    @Column(length = 255)
    private String location;

    @Column(name = "media_url", length = 500)
    private String mediaUrl;

    public Event() {
    }

    public Integer getEventId() {
        return eventId;
    }

    public User getOwner() {
        return owner;
    }

    public String getCameraId() {
        return cameraId;
    }
    public LocalDateTime getCaptureTime() {
        return captureTime;
    }

    public String getLocation() {
        return location;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}