package com.hotelbooking.repository;

import com.hotelbooking.entity.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class HotelSpecificationBuilder {

    public static Specification<Hotel> build(Long id, String name, String title, String city, 
                                           String address, BigDecimal distanceFromCenter, 
                                           BigDecimal rating, Integer numberOfRatings) {
        Specification<Hotel> spec = Specification.where(null);

        if (id != null) {
            spec = spec.and(hasId(id));
        }
        if (name != null && !name.trim().isEmpty()) {
            spec = spec.and(hasNameContaining(name));
        }
        if (title != null && !title.trim().isEmpty()) {
            spec = spec.and(hasTitleContaining(title));
        }
        if (city != null && !city.trim().isEmpty()) {
            spec = spec.and(hasCityContaining(city));
        }
        if (address != null && !address.trim().isEmpty()) {
            spec = spec.and(hasAddressContaining(address));
        }
        if (distanceFromCenter != null) {
            spec = spec.and(hasDistanceFromCenter(distanceFromCenter));
        }
        if (rating != null) {
            spec = spec.and(hasRating(rating));
        }
        if (numberOfRatings != null) {
            spec = spec.and(hasNumberOfRatings(numberOfRatings));
        }

        return spec;
    }

    private static Specification<Hotel> hasId(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    private static Specification<Hotel> hasNameContaining(String name) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), 
                        "%" + name.toLowerCase() + "%");
    }

    private static Specification<Hotel> hasTitleContaining(String title) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), 
                        "%" + title.toLowerCase() + "%");
    }

    private static Specification<Hotel> hasCityContaining(String city) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), 
                        "%" + city.toLowerCase() + "%");
    }

    private static Specification<Hotel> hasAddressContaining(String address) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), 
                        "%" + address.toLowerCase() + "%");
    }

    private static Specification<Hotel> hasDistanceFromCenter(BigDecimal distance) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("distanceFromCenter"), distance);
    }

    private static Specification<Hotel> hasRating(BigDecimal rating) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("rating"), rating);
    }

    private static Specification<Hotel> hasNumberOfRatings(Integer numberOfRatings) {
        return (root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("numberOfRatings"), numberOfRatings);
    }
}
