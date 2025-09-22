package com.hotelbooking.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

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

    // Constructors
    public RoomRequestDto() {}

    public RoomRequestDto(String name, String description, String roomNumber, BigDecimal price, 
                         Integer maxCapacity, Long hotelId) {
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.hotelId = hotelId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public List<String> getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(List<String> unavailableDates) {
        this.unavailableDates = unavailableDates;
    }
}
