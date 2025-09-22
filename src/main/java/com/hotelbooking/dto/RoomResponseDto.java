package com.hotelbooking.dto;

import java.math.BigDecimal;
import java.util.List;

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

    // Constructors
    public RoomResponseDto() {}

    public RoomResponseDto(Long id, String name, String description, String roomNumber, 
                          BigDecimal price, Integer maxCapacity, Long hotelId, String hotelName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roomNumber = roomNumber;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<String> getUnavailableDates() {
        return unavailableDates;
    }

    public void setUnavailableDates(List<String> unavailableDates) {
        this.unavailableDates = unavailableDates;
    }
}
