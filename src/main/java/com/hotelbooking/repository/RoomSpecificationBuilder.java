package com.hotelbooking.repository;

import com.hotelbooking.entity.Room;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RoomSpecificationBuilder {

    public static Specification<Room> build(Long id, String name, String roomNumber, 
                                          BigDecimal minPrice, BigDecimal maxPrice, 
                                          Integer maxCapacity, LocalDate checkInDate, 
                                          LocalDate checkOutDate, Long hotelId) {
        Specification<Room> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and(hasId(id));
        }
        if (name != null && !name.trim().isEmpty()) {
            spec = spec.and(hasNameContaining(name));
        }
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            spec = spec.and(hasRoomNumberContaining(roomNumber));
        }
        if (minPrice != null) {
            spec = spec.and(hasPriceGreaterThanOrEqual(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(hasPriceLessThanOrEqual(maxPrice));
        }
        if (maxCapacity != null) {
            spec = spec.and(hasMaxCapacity(maxCapacity));
        }
        if (hotelId != null) {
            spec = spec.and(hasHotelId(hotelId));
        }
        // Note: Date filtering for availability would need to be handled separately
        // as it requires checking against booking conflicts

        return spec;
    }

    private static Specification<Room> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<Room> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), 
                        "%" + name.toLowerCase() + "%");
    }

    private static Specification<Room> hasRoomNumberContaining(String roomNumber) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("roomNumber")), 
                        "%" + roomNumber.toLowerCase() + "%");
    }

    private static Specification<Room> hasPriceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Room> hasPriceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Room> hasMaxCapacity(Integer maxCapacity) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("maxCapacity"), maxCapacity);
    }

    private static Specification<Room> hasHotelId(Long hotelId) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("hotel").get("id"), hotelId);
    }
}
