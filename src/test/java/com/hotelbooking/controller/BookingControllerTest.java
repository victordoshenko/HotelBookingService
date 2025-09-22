package com.hotelbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testCreateBooking() throws Exception {
        BookingRequestDto requestDto = new BookingRequestDto(
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3), 1L
        );
        
        BookingResponseDto responseDto = new BookingResponseDto(
                1L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                1L, "Test Room", "101", 1L, "Test Hotel", 1L, "testuser"
        );

        when(bookingService.createBooking(any(BookingRequestDto.class), eq(1L))).thenReturn(responseDto);

        mockMvc.perform(post("/api/bookings")
                        .with(csrf())
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomName").value("Test Room"));
    }

    @Test
    @WithMockUser
    void testGetBookingById() throws Exception {
        BookingResponseDto responseDto = new BookingResponseDto(
                1L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                1L, "Test Room", "101", 1L, "Test Hotel", 1L, "testuser"
        );

        when(bookingService.getBookingById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomName").value("Test Room"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllBookings() throws Exception {
        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetUserBookings() throws Exception {
        mockMvc.perform(get("/api/bookings/user/1"))
                .andExpect(status().isOk());
    }
}
