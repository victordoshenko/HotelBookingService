package com.hotelbooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/").permitAll()
                // Admin only endpoints
                .requestMatchers("/api/hotels", "POST").hasRole("ADMIN")
                .requestMatchers("/api/hotels/*", "PUT").hasRole("ADMIN")
                .requestMatchers("/api/hotels/*", "DELETE").hasRole("ADMIN")
                .requestMatchers("/api/rooms", "POST").hasRole("ADMIN")
                .requestMatchers("/api/rooms/*", "PUT").hasRole("ADMIN")
                .requestMatchers("/api/rooms/*", "DELETE").hasRole("ADMIN")
                .requestMatchers("/api/bookings", "GET").hasRole("ADMIN")
                .requestMatchers("/api/statistics/**").hasRole("ADMIN")
                // Authenticated endpoints
                .requestMatchers("/api/hotels/**").authenticated()
                .requestMatchers("/api/rooms/**").authenticated()
                .requestMatchers("/api/bookings/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(httpBasic -> httpBasic.realmName("Hotel Booking API"));

        return http.build();
    }
}
