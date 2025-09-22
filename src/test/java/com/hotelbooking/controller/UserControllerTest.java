package com.hotelbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.config.TestSecurityConfig;
import com.hotelbooking.dto.UserRequestDto;
import com.hotelbooking.dto.UserResponseDto;
import com.hotelbooking.entity.UserRole;
import com.hotelbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        UserRequestDto requestDto = new UserRequestDto(
                "testuser", "password123", "test@example.com", UserRole.USER
        );
        
        UserResponseDto responseDto = new UserResponseDto(
                1L, "testuser", "test@example.com", UserRole.USER
        );

        when(userService.createUser(any(UserRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser
    void testGetUserById() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(
                1L, "testuser", "test@example.com", UserRole.USER
        );

        when(userService.getUserById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockUser
    void testGetUserByUsername() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(
                1L, "testuser", "test@example.com", UserRole.USER
        );

        when(userService.getUserByUsername("testuser")).thenReturn(responseDto);

        mockMvc.perform(get("/api/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
