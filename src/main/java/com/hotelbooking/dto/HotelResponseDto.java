package com.hotelbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDto {

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private BigDecimal distanceFromCenter;
    private BigDecimal rating;
    private Integer numberOfRatings;
}
