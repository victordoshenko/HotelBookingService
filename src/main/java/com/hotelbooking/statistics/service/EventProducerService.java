package com.hotelbooking.statistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.statistics.event.BookingEvent;
import com.hotelbooking.statistics.event.UserRegistrationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EventProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserRegistrationEvent(UserRegistrationEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("user-registration-events", eventJson);
        } catch (Exception e) {
            System.err.println("Error sending user registration event: " + e.getMessage());
        }
    }

    public void sendBookingEvent(BookingEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("booking-events", eventJson);
        } catch (Exception e) {
            System.err.println("Error sending booking event: " + e.getMessage());
        }
    }
}
