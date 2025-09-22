package com.hotelbooking.statistics.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class UserRegistrationEvent {
    
    @JsonProperty("event_type")
    private String eventType = "USER_REGISTRATION";
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    // Constructors
    public UserRegistrationEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public UserRegistrationEvent(Long userId) {
        this();
        this.userId = userId;
    }
    
    // Getters and Setters
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
