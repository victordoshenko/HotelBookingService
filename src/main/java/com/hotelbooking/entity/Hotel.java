package com.hotelbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel name is required")
    @Size(max = 255, message = "Hotel name must not exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Hotel title is required")
    @Size(max = 255, message = "Hotel title must not exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City name must not exceed 100 characters")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Distance from center is required")
    @DecimalMin(value = "0.0", message = "Distance from center must be non-negative")
    @Column(name = "distance_from_center", nullable = false, precision = 10, scale = 2)
    private BigDecimal distanceFromCenter;

    @DecimalMin(value = "1.0", message = "Rating must be at least 1.0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5.0")
    @Column(precision = 3, scale = 1)
    private BigDecimal rating = BigDecimal.valueOf(1.0);

    @Min(value = 0, message = "Number of ratings must be non-negative")
    @Column(name = "number_of_ratings")
    private Integer numberOfRatings = 0;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<com.hotelbooking.entity.Room> rooms = new ArrayList<>();

    // Constructors
    public Hotel() {}

    public Hotel(String name, String title, String city, String address, BigDecimal distanceFromCenter) {
        this.name = name;
        this.title = title;
        this.city = city;
        this.address = address;
        this.distanceFromCenter = distanceFromCenter;
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

    public List<com.hotelbooking.entity.Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<com.hotelbooking.entity.Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", distanceFromCenter=" + distanceFromCenter +
                ", rating=" + rating +
                ", numberOfRatings=" + numberOfRatings +
                '}';
    }
}
