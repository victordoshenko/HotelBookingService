package com.hotelbooking.service;

import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.HotelResponseDto;
import com.hotelbooking.dto.PageResponseDto;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.exception.HotelNotFoundException;
import com.hotelbooking.mapper.HotelMapper;
import com.hotelbooking.repository.HotelRepository;
import com.hotelbooking.repository.HotelSpecificationBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Autowired
    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    public HotelResponseDto createHotel(HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelMapper.toEntity(hotelRequestDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(savedHotel);
    }

    public HotelResponseDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toResponseDto(hotel);
    }

    public HotelResponseDto updateHotel(Long id, HotelRequestDto hotelRequestDto) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
        
        hotelMapper.updateEntityFromDto(hotelRequestDto, existingHotel);
        Hotel updatedHotel = hotelRepository.save(existingHotel);
        return hotelMapper.toResponseDto(updatedHotel);
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    public PageResponseDto<HotelResponseDto> getAllHotels(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> hotelPage = hotelRepository.findAll(pageable);
        
        List<HotelResponseDto> hotelDtos = hotelPage.getContent()
                .stream()
                .map(hotelMapper::toResponseDto)
                .collect(Collectors.toList());
        
        return new PageResponseDto<>(
                hotelDtos,
                hotelPage.getNumber(),
                hotelPage.getSize(),
                hotelPage.getTotalElements(),
                hotelPage.getTotalPages(),
                hotelPage.isFirst(),
                hotelPage.isLast()
        );
    }

    public PageResponseDto<HotelResponseDto> searchHotels(Specification<Hotel> specification, 
                                                         int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Hotel> hotelPage = hotelRepository.findAll(specification, pageable);
        
        List<HotelResponseDto> hotelDtos = hotelPage.getContent()
                .stream()
                .map(hotelMapper::toResponseDto)
                .collect(Collectors.toList());
        
        return new PageResponseDto<>(
                hotelDtos,
                hotelPage.getNumber(),
                hotelPage.getSize(),
                hotelPage.getTotalElements(),
                hotelPage.getTotalPages(),
                hotelPage.isFirst(),
                hotelPage.isLast()
        );
    }

    public PageResponseDto<HotelResponseDto> searchHotelsWithParams(
            Long id, String name, String title, String city, String address,
            BigDecimal distanceFromCenter, BigDecimal rating, Integer numberOfRatings,
            int page, int size, String sortBy, String sortDir) {
        
        Specification<Hotel> specification = HotelSpecificationBuilder.build(
                id, name, title, city, address, distanceFromCenter, rating, numberOfRatings);
        
        return searchHotels(specification, page, size, sortBy, sortDir);
    }

    public HotelResponseDto updateHotelRating(Long id, BigDecimal newRating) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
        
        if (newRating.compareTo(BigDecimal.ONE) < 0 || newRating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        // Calculate new rating according to the formula
        BigDecimal currentRating = hotel.getRating();
        Integer currentNumberOfRatings = hotel.getNumberOfRatings();
        
        // totalRating = rating × numberOfRating
        BigDecimal totalRating = currentRating.multiply(BigDecimal.valueOf(currentNumberOfRatings));
        
        // totalRating = totalRating − rating + newMark
        totalRating = totalRating.subtract(currentRating).add(newRating);
        
        // rating = totalRating / numberOfRating (rounded to 1 decimal place)
        BigDecimal newAverageRating = totalRating.divide(BigDecimal.valueOf(currentNumberOfRatings), 1, RoundingMode.HALF_UP);
        
        // numberOfRating = numberOfRating + 1
        Integer newNumberOfRatings = currentNumberOfRatings + 1;
        
        hotel.setRating(newAverageRating);
        hotel.setNumberOfRatings(newNumberOfRatings);
        
        Hotel updatedHotel = hotelRepository.save(hotel);
        return hotelMapper.toResponseDto(updatedHotel);
    }
}
