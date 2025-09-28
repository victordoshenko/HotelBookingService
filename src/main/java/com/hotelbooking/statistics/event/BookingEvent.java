package com.hotelbooking.statistics.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDateTime timestamp = LocalDateTime.now();
    
    public BookingEvent(Long userId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.timestamp = LocalDateTime.now();
    }
}
