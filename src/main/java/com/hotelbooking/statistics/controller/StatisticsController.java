package com.hotelbooking.statistics.controller;

import com.hotelbooking.statistics.entity.StatisticsEvent;
import com.hotelbooking.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StatisticsEvent>> getAllEvents() {
        List<StatisticsEvent> events = statisticsService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/type/{eventType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StatisticsEvent>> getEventsByType(@PathVariable String eventType) {
        List<StatisticsEvent> events = statisticsService.getEventsByType(eventType);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportToCsv() {
        try {
            byte[] csvData = statisticsService.exportToCsv();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "statistics.csv");
            headers.setContentLength(csvData.length);
            
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(csvData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
