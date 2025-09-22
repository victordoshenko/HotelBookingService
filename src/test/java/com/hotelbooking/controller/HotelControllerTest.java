package com.hotelbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.HotelResponseDto;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.mapper.HotelMapper;
import com.hotelbooking.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @MockBean
    private HotelMapper hotelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateHotel() throws Exception {
        HotelRequestDto requestDto = new HotelRequestDto(
                "Test Hotel", "Test Title", "Test City", "Test Address", BigDecimal.valueOf(5.0)
        );
        
        HotelResponseDto responseDto = new HotelResponseDto(
                1L, "Test Hotel", "Test Title", "Test City", "Test Address", 
                BigDecimal.valueOf(5.0), BigDecimal.valueOf(1.0), 0
        );

        when(hotelService.createHotel(any(HotelRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/hotels")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    @WithMockUser
    void testGetHotelById() throws Exception {
        HotelResponseDto responseDto = new HotelResponseDto(
                1L, "Test Hotel", "Test Title", "Test City", "Test Address", 
                BigDecimal.valueOf(5.0), BigDecimal.valueOf(1.0), 0
        );

        when(hotelService.getHotelById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateHotel() throws Exception {
        HotelRequestDto requestDto = new HotelRequestDto(
                "Updated Hotel", "Updated Title", "Updated City", "Updated Address", BigDecimal.valueOf(3.0)
        );
        
        HotelResponseDto responseDto = new HotelResponseDto(
                1L, "Updated Hotel", "Updated Title", "Updated City", "Updated Address", 
                BigDecimal.valueOf(3.0), BigDecimal.valueOf(1.0), 0
        );

        when(hotelService.updateHotel(eq(1L), any(HotelRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/hotels/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Hotel"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteHotel() throws Exception {
        mockMvc.perform(delete("/api/hotels/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testUpdateHotelRating() throws Exception {
        HotelResponseDto responseDto = new HotelResponseDto(
                1L, "Test Hotel", "Test Title", "Test City", "Test Address", 
                BigDecimal.valueOf(5.0), BigDecimal.valueOf(4.5), 1
        );

        when(hotelService.updateHotelRating(1L, BigDecimal.valueOf(5))).thenReturn(responseDto);

        mockMvc.perform(put("/api/hotels/1/rating")
                        .with(csrf())
                        .param("rating", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rating").value(4.5))
                .andExpect(jsonPath("$.numberOfRatings").value(1));
    }
}
