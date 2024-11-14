package com.example.ticketingSystem.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddConfigReqDTO {
    private int no_of_tickets;
    private int ticket_release_rate;
    private int customer_retrieval_rate;
    private int max_tickets;
}