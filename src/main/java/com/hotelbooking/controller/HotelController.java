package com.hotelbooking.controller;

import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.HotelResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public HotelResponseDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponseDto createHotel(@Valid @RequestBody HotelRequestDto hotelRequestDto) {
        return hotelService.createHotel(hotelRequestDto);
    }

    @PutMapping("/{id}")
    public HotelResponseDto updateHotel(
            @PathVariable Long id, 
            @Valid @RequestBody HotelRequestDto hotelRequestDto) {
        return hotelService.updateHotel(id, hotelRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }

    @GetMapping
    public PageResponseDto<HotelResponseDto> getAllHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        return hotelService.getAllHotels(page, size, sortBy, sortDir);
    }

    @PutMapping("/{id}/rating")
    public HotelResponseDto updateHotelRating(
            @PathVariable Long id, 
            @RequestParam BigDecimal rating) {
        return hotelService.updateHotelRating(id, rating);
    }

    @GetMapping("/search")
    public PageResponseDto<HotelResponseDto> searchHotels(
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
        
        return hotelService.searchHotelsWithParams(
                id, name, title, city, address, distanceFromCenter, rating, numberOfRatings,
                page, size, sortBy, sortDir);
    }
}
