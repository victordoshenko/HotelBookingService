package com.hotelbooking.service;

import com.hotelbooking.dto.RoomRequestDto;
import com.hotelbooking.dto.RoomResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.Room;
import com.hotelbooking.exception.HotelNotFoundException;
import com.hotelbooking.exception.RoomNotFoundException;
import com.hotelbooking.mapper.RoomMapper;
import com.hotelbooking.repository.HotelRepository;
import com.hotelbooking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.roomMapper = roomMapper;
    }

    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId()));
        
        Room room = roomMapper.toEntity(roomRequestDto);
        room.setHotel(hotel);
        
        // Convert string dates to LocalDate if provided
        if (roomRequestDto.getUnavailableDates() != null) {
            List<LocalDate> unavailableDates = roomRequestDto.getUnavailableDates().stream()
                    .map(LocalDate::parse)
                    .collect(Collectors.toList());
            room.setUnavailableDates(unavailableDates);
        }
        
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponseDto(savedRoom);
    }

    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        return roomMapper.toResponseDto(room);
    }

    public RoomResponseDto updateRoom(Long id, RoomRequestDto roomRequestDto) {
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + id));
        
        Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + roomRequestDto.getHotelId()));
        
        roomMapper.updateEntityFromDto(roomRequestDto, existingRoom);
        existingRoom.setHotel(hotel);
        
        // Convert string dates to LocalDate if provided
        if (roomRequestDto.getUnavailableDates() != null) {
            List<LocalDate> unavailableDates = roomRequestDto.getUnavailableDates().stream()
                    .map(LocalDate::parse)
                    .collect(Collectors.toList());
            existingRoom.setUnavailableDates(unavailableDates);
        }
        
        Room updatedRoom = roomRepository.save(existingRoom);
        return roomMapper.toResponseDto(updatedRoom);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RoomNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    public PageResponseDto<RoomResponseDto> searchRooms(Specification<Room> specification, 
                                                       int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Room> roomPage = roomRepository.findAll(specification, pageable);
        
        List<RoomResponseDto> roomDtos = roomPage.getContent()
                .stream()
                .map(roomMapper::toResponseDto)
                .collect(Collectors.toList());
        
        return new PageResponseDto<>(
                roomDtos,
                roomPage.getNumber(),
                roomPage.getSize(),
                roomPage.getTotalElements(),
                roomPage.getTotalPages(),
                roomPage.isFirst(),
                roomPage.isLast()
        );
    }

    public List<RoomResponseDto> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Room> availableRooms = roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate);
        return availableRooms.stream()
                .map(roomMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
