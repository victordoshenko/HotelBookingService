package com.hotelbooking.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

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

    // Constructors
    public HotelRequestDto() {}

    public HotelRequestDto(String name, String title, String city, String address, BigDecimal distanceFromCenter) {
        this.name = name;
        this.title = title;
        this.city = city;
        this.address = address;
        this.distanceFromCenter = distanceFromCenter;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getDistanceFromCenter() {
        return distanceFromCenter;
    }

    public void setDistanceFromCenter(BigDecimal distanceFromCenter) {
        this.distanceFromCenter = distanceFromCenter;
    }
}
