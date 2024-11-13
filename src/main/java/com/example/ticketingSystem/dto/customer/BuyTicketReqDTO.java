package com.example.ticketingSystem.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketReqDTO {
    private int requested_tickets_buy;
}
