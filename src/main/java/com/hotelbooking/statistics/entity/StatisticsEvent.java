package com.hotelbooking.statistics.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "statistics_events")
public class StatisticsEvent {
    
    @Id
    private String id;
    
    private String eventType;
    private String eventData;
    private LocalDateTime timestamp;
    
    // Constructors
    public StatisticsEvent() {
        this.timestamp = LocalDateTime.now();
    }
    
    public StatisticsEvent(String eventType, String eventData) {
        this();
        this.eventType = eventType;
        this.eventData = eventData;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public String getEventData() {
        return eventData;
    }
    
    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
