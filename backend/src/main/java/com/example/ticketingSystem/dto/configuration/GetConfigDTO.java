package com.example.ticketingSystem.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetConfigDTO {

    private Long config_id;
    private String eventName;
    private String location;
    private int currentTicketCount;
}
