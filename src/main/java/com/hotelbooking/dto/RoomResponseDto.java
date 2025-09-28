package com.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {

    private Long id;
    private String name;
    private String description;
    private String roomNumber;
    private BigDecimal price;
    private Integer maxCapacity;
    private Long hotelId;
    private String hotelName;
    private List<String> unavailableDates;
}
