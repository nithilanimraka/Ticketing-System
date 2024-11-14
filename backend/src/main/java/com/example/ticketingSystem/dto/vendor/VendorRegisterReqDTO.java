package com.example.ticketingSystem.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRegisterReqDTO {
    private String name;
    private String email;
    private String username;
    private String password;
}
