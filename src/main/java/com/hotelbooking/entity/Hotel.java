package com.hotelbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
