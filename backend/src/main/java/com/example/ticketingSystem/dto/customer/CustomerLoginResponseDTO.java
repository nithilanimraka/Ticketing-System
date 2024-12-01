package com.example.ticketingSystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginResponseDTO {
    private String message;
    private String username;
    private Boolean status;
}
