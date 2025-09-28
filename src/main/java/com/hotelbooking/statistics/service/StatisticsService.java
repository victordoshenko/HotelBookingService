package com.hotelbooking.statistics.service;

import com.hotelbooking.statistics.entity.StatisticsEvent;
import com.hotelbooking.statistics.repository.StatisticsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class StatisticsService {

    private final StatisticsEventRepository statisticsEventRepository;

    @Autowired
    public StatisticsService(StatisticsEventRepository statisticsEventRepository) {
        this.statisticsEventRepository = statisticsEventRepository;
    }

    @KafkaListener(topics = "user-registration-events", groupId = "hotel-booking-stats")
    public void handleUserRegistrationEvent(String eventData) {
        try {
            StatisticsEvent event = new StatisticsEvent("USER_REGISTRATION", eventData);
            statisticsEventRepository.save(event);
        } catch (Exception e) {
            // Log error
            System.err.println("Error processing user registration event: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "booking-events", groupId = "hotel-booking-stats")
    public void handleBookingEvent(String eventData) {
        try {
            StatisticsEvent event = new StatisticsEvent("BOOKING", eventData);
            statisticsEventRepository.save(event);
        } catch (Exception e) {
            // Log error
            System.err.println("Error processing booking event: " + e.getMessage());
        }
    }

    public List<StatisticsEvent> getAllEvents() {
        return statisticsEventRepository.findAll();
    }

    public List<StatisticsEvent> getEventsByType(String eventType) {
        return statisticsEventRepository.findByEventType(eventType);
    }

    public List<StatisticsEvent> getEventsByDateRange(LocalDateTime start, LocalDateTime end) {
        return statisticsEventRepository.findByTimestampBetween(start, end);
    }

    public byte[] exportToCsv() throws IOException {
        List<StatisticsEvent> events = statisticsEventRepository.findAll();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);
        
        // Write CSV header
        writer.println("ID,Event Type,Event Data,Timestamp");
        
        // Write data rows
        for (StatisticsEvent event : events) {
            writer.printf("%s,%s,\"%s\",%s%n",
                    event.getId(),
                    event.getEventType(),
                    event.getEventData().replace("\"", "\"\""), // Escape quotes
                    event.getTimestamp()
            );
        }
        
        writer.flush();
        writer.close();
        
        return outputStream.toByteArray();
    }

    public byte[] exportToCsvWithHeaders(HttpServletResponse response) throws IOException {
        byte[] csvData = exportToCsv();
        
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=statistics.csv");
        response.setContentLength(csvData.length);
        
        return csvData;
    }
}
