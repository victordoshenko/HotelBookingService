package com.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDto {

    @NotBlank(message = "Hotel name is required")
    @Size(max = 255, message = "Hotel name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Hotel title is required")
    @Size(max = 255, message = "Hotel title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City name must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;

    @NotNull(message = "Distance from center is required")
    @DecimalMin(value = "0.0", message = "Distance from center must be non-negative")
    private BigDecimal distanceFromCenter;
}
