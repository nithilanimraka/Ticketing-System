package com.example.ticketingSystem.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetConfigDTO {
    private String eventName;
    private String location;
    private int no_of_tickets;
}
