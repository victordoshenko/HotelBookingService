package com.hotelbooking.statistics.controller;

import com.hotelbooking.statistics.entity.StatisticsEvent;
import com.hotelbooking.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

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
    public List<StatisticsEvent> getAllEvents() {
        return statisticsService.getAllEvents();
    }

    @GetMapping("/type/{eventType}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<StatisticsEvent> getEventsByType(@PathVariable String eventType) {
        return statisticsService.getEventsByType(eventType);
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public byte[] exportToCsv(HttpServletResponse response) throws IOException {
        return statisticsService.exportToCsvWithHeaders(response);
    }
}
