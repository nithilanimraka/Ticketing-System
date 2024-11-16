package com.example.ticketingSystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketResponseDTO {
    String message;
    Boolean status;
}
