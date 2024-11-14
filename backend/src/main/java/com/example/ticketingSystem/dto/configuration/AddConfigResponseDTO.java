package com.example.ticketingSystem.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddConfigResponseDTO {
    private Long id;
    private String message;
}