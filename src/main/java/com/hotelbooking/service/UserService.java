package com.hotelbooking.service;

import com.hotelbooking.dto.UserRequestDto;
import com.hotelbooking.dto.UserResponseDto;
import com.hotelbooking.entity.User;
import com.hotelbooking.exception.UserAlreadyExistsException;
import com.hotelbooking.exception.UserNotFoundException;
import com.hotelbooking.mapper.UserMapper;
import com.hotelbooking.repository.UserRepository;
import com.hotelbooking.statistics.event.UserRegistrationEvent;
import com.hotelbooking.statistics.service.EventProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EventProducerService eventProducerService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, EventProducerService eventProducerService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventProducerService = eventProducerService;
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        // Check if user already exists
        if (userRepository.existsByUsernameOrEmail(userRequestDto.getUsername(), userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("User with this username or email already exists");
        }
        
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        User savedUser = userRepository.save(user);
        
        // Send user registration event
        UserRegistrationEvent event = new UserRegistrationEvent(savedUser.getId());
        eventProducerService.sendUserRegistrationEvent(event);
        
        return userMapper.toResponseDto(savedUser);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userMapper.toResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        // Check if username or email is already taken by another user
        if (!existingUser.getUsername().equals(userRequestDto.getUsername()) && 
            userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        
        if (!existingUser.getEmail().equals(userRequestDto.getEmail()) && 
            userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        
        userMapper.updateEntityFromDto(userRequestDto, existingUser);
        existingUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}
