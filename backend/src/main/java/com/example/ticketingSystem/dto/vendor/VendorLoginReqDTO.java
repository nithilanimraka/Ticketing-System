package com.example.ticketingSystem.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorLoginReqDTO {
//    private Long id;
    private String username;
    private String password;
}