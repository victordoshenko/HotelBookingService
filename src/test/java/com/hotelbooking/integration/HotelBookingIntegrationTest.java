package com.hotelbooking.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.UserRequestDto;
import com.hotelbooking.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class HotelBookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCompleteBookingFlow() throws Exception {
        // 1. Register a user
        UserRequestDto userRequest = new UserRequestDto(
                "testuser", "password123", "test@example.com", UserRole.USER
        );

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated());

        // 2. Create a hotel (as admin)
        HotelRequestDto hotelRequest = new HotelRequestDto(
                "Test Hotel", "Test Title", "Test City", "Test Address", BigDecimal.valueOf(5.0)
        );

        mockMvc.perform(post("/api/hotels")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelRequest)))
                .andExpect(status().isCreated());

        // 3. Get hotels (as user)
        mockMvc.perform(get("/api/hotels")
                        .with(httpBasic("testuser", "password123")))
                .andExpect(status().isOk());
    }
}
