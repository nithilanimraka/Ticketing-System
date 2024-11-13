package com.example.ticketingSystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRegisterResponseDTO {
    private Long id;
    private String message;
}
