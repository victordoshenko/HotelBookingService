package com.hotelbooking.mapper;

import com.hotelbooking.dto.UserRequestDto;
import com.hotelbooking.dto.UserResponseDto;
import com.hotelbooking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    void updateEntityFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}
