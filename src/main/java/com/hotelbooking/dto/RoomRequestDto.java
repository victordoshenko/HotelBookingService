package com.hotelbooking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {

    @NotBlank(message = "Room name is required")
    @Size(max = 255, message = "Room name must not exceed 255 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Room number is required")
    @Size(max = 50, message = "Room number must not exceed 50 characters")
    private String roomNumber;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    private BigDecimal price;

    @NotNull(message = "Maximum capacity is required")
    @Min(value = 1, message = "Maximum capacity must be at least 1")
    @Max(value = 20, message = "Maximum capacity must not exceed 20")
    private Integer maxCapacity;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    private List<String> unavailableDates;
}
