package com.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private String roomName;
    private String roomNumber;
    private Long hotelId;
    private String hotelName;
    private Long userId;
    private String username;
}
