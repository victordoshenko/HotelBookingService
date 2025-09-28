package com.hotelbooking.service;

import com.hotelbooking.dto.BookingRequestDto;
import com.hotelbooking.dto.BookingResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.entity.Booking;
import com.hotelbooking.entity.Room;
import com.hotelbooking.entity.User;
import com.hotelbooking.exception.RoomNotFoundException;
import com.hotelbooking.exception.UserNotFoundException;
import com.hotelbooking.exception.RoomNotAvailableException;
import com.hotelbooking.mapper.BookingMapper;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.RoomRepository;
import com.hotelbooking.repository.UserRepository;
import com.hotelbooking.statistics.event.BookingEvent;
import com.hotelbooking.statistics.service.EventProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final EventProducerService eventProducerService;

    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto, Long userId) {
        // Validate dates
        if (bookingRequestDto.getCheckInDate().isAfter(bookingRequestDto.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        if (bookingRequestDto.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date must be in the future");
        }
        
        // Find room and user
        Room room = roomRepository.findById(bookingRequestDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + bookingRequestDto.getRoomId()));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        // Check if room is available
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(
                bookingRequestDto.getRoomId(), 
                bookingRequestDto.getCheckInDate(), 
                bookingRequestDto.getCheckOutDate()
        );
        
        if (!conflictingBookings.isEmpty()) {
            throw new RoomNotAvailableException("Room is not available for the selected dates");
        }
        
        // Create booking
        Booking booking = bookingMapper.toEntity(bookingRequestDto);
        booking.setRoom(room);
        booking.setUser(user);
        
        Booking savedBooking = bookingRepository.save(booking);
        
        // Send booking event
        BookingEvent event = new BookingEvent(userId, bookingRequestDto.getCheckInDate(), bookingRequestDto.getCheckOutDate());
        eventProducerService.sendBookingEvent(event);
        
        return bookingMapper.toResponseDto(savedBooking);
    }

    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return bookingMapper.toResponseDto(booking);
    }

    public PageResponseDto<BookingResponseDto> getAllBookings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Booking> bookingPage = bookingRepository.findAllWithPagination(pageable);
        
        List<BookingResponseDto> bookingDtos = bookingPage.getContent()
                .stream()
                .map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
        
        return new PageResponseDto<>(
                bookingDtos,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements(),
                bookingPage.getTotalPages(),
                bookingPage.isFirst(),
                bookingPage.isLast()
        );
    }

    public List<BookingResponseDto> getUserBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
