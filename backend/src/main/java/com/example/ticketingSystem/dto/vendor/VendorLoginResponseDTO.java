package com.example.ticketingSystem.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorLoginResponseDTO {
    private String message;
    private Boolean status;
}
