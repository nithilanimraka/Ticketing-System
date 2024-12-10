package com.example.ticketingSystem.dto.configuration;

import com.example.ticketingSystem.entity.Vendor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddConfigReqDTO {
    private String eventName;
    private String location;
    private int no_of_tickets;
    private int ticket_release_rate;
    private int customer_retrieval_rate;
    private int max_tickets;
}