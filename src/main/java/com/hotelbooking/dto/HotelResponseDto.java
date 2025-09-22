package com.hotelbooking.dto;

import java.math.BigDecimal;

public class HotelResponseDto {

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private BigDecimal distanceFromCenter;
    private BigDecimal rating;
    private Integer numberOfRatings;

    // Constructors
    public HotelResponseDto() {}

    public HotelResponseDto(Long id, String name, String title, String city, String address, 
                           BigDecimal distanceFromCenter, BigDecimal rating, Integer numberOfRatings) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.city = city;
        this.address = address;
        this.distanceFromCenter = distanceFromCenter;
        this.rating = rating;
        this.numberOfRatings = numberOfRatings;
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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }
}
