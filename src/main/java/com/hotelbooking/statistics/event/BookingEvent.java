package com.hotelbooking.statistics.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingEvent {
    
    @JsonProperty("event_type")
    private String eventType = "BOOKING";
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("check_in_date")
    private LocalDate checkInDate;
    
    @JsonProperty("check_out_date")
    private LocalDate checkOutDate;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    // Constructors
    public BookingEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public BookingEvent(Long userId, LocalDate checkInDate, LocalDate checkOutDate) {
        this();
        this.userId = userId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
