package com.example.ticketingSystem.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTicketResponseDTO {
    String message;
    Boolean status;
}
