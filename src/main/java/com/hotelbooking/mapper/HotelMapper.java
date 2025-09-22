package com.hotelbooking.mapper;

import com.hotelbooking.dto.HotelRequestDto;
import com.hotelbooking.dto.HotelResponseDto;
import com.hotelbooking.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelRequestDto hotelRequestDto);

    HotelResponseDto toResponseDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "numberOfRatings", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    void updateEntityFromDto(HotelRequestDto hotelRequestDto, @MappingTarget Hotel hotel);
}
