package com.hotelbooking.controller;

import com.hotelbooking.dto.RoomRequestDto;
import com.hotelbooking.dto.RoomResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) {
        RoomResponseDto room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@Valid @RequestBody RoomRequestDto roomRequestDto) {
        RoomResponseDto createdRoom = roomService.createRoom(roomRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(
            @PathVariable Long id, 
            @Valid @RequestBody RoomRequestDto roomRequestDto) {
        RoomResponseDto updatedRoom = roomService.updateRoom(id, roomRequestDto);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDto<RoomResponseDto>> searchRooms(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String checkInDate,
            @RequestParam(required = false) String checkOutDate,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        java.math.BigDecimal minPriceDecimal = minPrice != null ? new java.math.BigDecimal(minPrice) : null;
        java.math.BigDecimal maxPriceDecimal = maxPrice != null ? new java.math.BigDecimal(maxPrice) : null;
        java.time.LocalDate checkIn = checkInDate != null ? java.time.LocalDate.parse(checkInDate) : null;
        java.time.LocalDate checkOut = checkOutDate != null ? java.time.LocalDate.parse(checkOutDate) : null;
        
        org.springframework.data.jpa.domain.Specification<com.hotelbooking.entity.Room> specification = 
                RoomSpecificationBuilder.build(id, name, roomNumber, minPriceDecimal, maxPriceDecimal, 
                                             maxCapacity, checkIn, checkOut, hotelId);
        
        PageResponseDto<RoomResponseDto> rooms = roomService.searchRooms(
                specification, page, size, sortBy, sortDir);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomResponseDto>> getAvailableRooms(
            @RequestParam Long hotelId,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {
        
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);
        
        List<RoomResponseDto> availableRooms = roomService.getAvailableRooms(hotelId, checkIn, checkOut);
        return ResponseEntity.ok(availableRooms);
    }
}
