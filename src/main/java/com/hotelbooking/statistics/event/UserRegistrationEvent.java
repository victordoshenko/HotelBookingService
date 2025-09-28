package com.hotelbooking.statistics.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationEvent {
    
    @JsonProperty("event_type")
    private String eventType = "USER_REGISTRATION";
    
    @JsonProperty("user_id")
    private Long userId;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public UserRegistrationEvent(Long userId) {
        this.userId = userId;
        this.timestamp = LocalDateTime.now();
    }
}
