package com.example.ticketingSystem.controller;

import com.example.ticketingSystem.dto.vendor.*;
import com.example.ticketingSystem.service.VendorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendor")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VendorController {
    private VendorService vendorService;

    @PostMapping("/register")
    public VendorRegisterResponseDTO register(@RequestBody VendorRegisterReqDTO vendorRegisterReqDTO){
        return vendorService.register(vendorRegisterReqDTO);
    }

    @PostMapping("/login")
    public VendorLoginResponseDTO login(@RequestBody VendorLoginReqDTO vendorLoginReqDTO){
        return vendorService.login(vendorLoginReqDTO);
    }

    @PostMapping("/add-tickets")
    public AddTicketResponseDTO addTickets(@RequestBody AddTicketReqDTO addTicketReqDTO){
        return vendorService.addTickets(addTicketReqDTO);
    }

    @DeleteMapping("/delete")
    public String deleteVendor(@RequestBody VendorDeleteReqDTO vendorDeleteReqDTO){
        return vendorService.deleteVendor(vendorDeleteReqDTO);
    }
}
