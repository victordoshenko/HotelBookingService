package com.hotelbooking.statistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "statistics_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsEvent {
    
    @Id
    private String id;
    
    private String eventType;
    private String eventData;
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public StatisticsEvent(String eventType, String eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
        this.timestamp = LocalDateTime.now();
    }
}
