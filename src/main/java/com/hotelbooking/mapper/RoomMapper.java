package com.hotelbooking.mapper;

import com.hotelbooking.dto.RoomRequestDto;
import com.hotelbooking.dto.RoomResponseDto;
import com.hotelbooking.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "unavailableDates", ignore = true)
    Room toEntity(RoomRequestDto roomRequestDto);

    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "hotelName", source = "hotel.name")
    @Mapping(target = "unavailableDates", expression = "java(convertDatesToStrings(room.getUnavailableDates()))")
    RoomResponseDto toResponseDto(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "unavailableDates", ignore = true)
    void updateEntityFromDto(RoomRequestDto roomRequestDto, @MappingTarget Room room);

    default List<String> convertDatesToStrings(List<LocalDate> dates) {
        if (dates == null) {
            return null;
        }
        return dates.stream()
                .map(LocalDate::toString)
                .toList();
    }
}
