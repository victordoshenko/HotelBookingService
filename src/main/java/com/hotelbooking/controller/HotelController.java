package com.hotelbooking.controller;

import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.HotelResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDto> getHotelById(@PathVariable Long id) {
        HotelResponseDto hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotel);
    }

    @PostMapping
    public ResponseEntity<HotelResponseDto> createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto) {
        HotelResponseDto createdHotel = hotelService.createHotel(hotelRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDto> updateHotel(
            @PathVariable Long id, 
            @Valid @RequestBody HotelRequestDto hotelRequestDto) {
        HotelResponseDto updatedHotel = hotelService.updateHotel(id, hotelRequestDto);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<HotelResponseDto>> getAllHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        PageResponseDto<HotelResponseDto> hotels = hotelService.getAllHotels(page, size, sortBy, sortDir);
        return ResponseEntity.ok(hotels);
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<HotelResponseDto> updateHotelRating(
            @PathVariable Long id, 
            @RequestParam BigDecimal rating) {
        HotelResponseDto updatedHotel = hotelService.updateHotelRating(id, rating);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<HotelResponseDto>> searchHotels(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) BigDecimal distanceFromCenter,
            @RequestParam(required = false) BigDecimal rating,
            @RequestParam(required = false) Integer numberOfRatings,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Specification<Hotel> specification = HotelSpecificationBuilder.build(
                id, name, title, city, address, distanceFromCenter, rating, numberOfRatings);
        
        PageResponseDto<HotelResponseDto> hotels = hotelService.searchHotels(
                specification, page, size, sortBy, sortDir);
        return ResponseEntity.ok(hotels);
    }
}
