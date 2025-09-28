package com.hotelbooking.dto;

import com.hotelbooking.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private UserRole role;
}
