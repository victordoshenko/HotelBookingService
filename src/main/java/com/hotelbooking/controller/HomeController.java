package com.hotelbooking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Hotel Booking Service API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("hotels", "/api/hotels");
        endpoints.put("rooms", "/api/rooms");
        endpoints.put("users", "/api/users");
        endpoints.put("bookings", "/api/bookings");
        endpoints.put("register", "/api/users/register");
        
        response.put("available_endpoints", endpoints);
        return response;
    }
}
