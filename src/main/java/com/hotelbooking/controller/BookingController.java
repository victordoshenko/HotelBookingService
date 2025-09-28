package com.hotelbooking.controller;

import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDto createBooking(
            @Valid @RequestBody BookingRequestDto bookingRequestDto,
            @RequestParam Long userId) {
        return bookingService.createBooking(bookingRequestDto, userId);
    }

    @GetMapping("/{id}")
    public BookingResponseDto getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping
    public PageResponseDto<BookingResponseDto> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookingService.getAllBookings(page, size);
    }

    @GetMapping("/user/{userId}")
    public List<BookingResponseDto> getUserBookings(@PathVariable Long userId) {
        return bookingService.getUserBookings(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}
