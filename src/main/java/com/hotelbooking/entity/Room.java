package com.hotelbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room name is required")
    @Size(max = 255, message = "Room name must not exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Room number is required")
    @Size(max = 50, message = "Room number must not exceed 50 characters")
    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Maximum capacity is required")
    @Min(value = 1, message = "Maximum capacity must be at least 1")
    @Max(value = 20, message = "Maximum capacity must not exceed 20")
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @ElementCollection
    @CollectionTable(name = "room_unavailable_dates", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "unavailable_date")
    private List<LocalDate> unavailableDates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();
}
