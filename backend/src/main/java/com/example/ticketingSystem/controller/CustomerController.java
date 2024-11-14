package com.example.ticketingSystem.controller;

import com.example.ticketingSystem.dto.customer.*;
import com.example.ticketingSystem.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {
    private CustomerService customerService;

    @PostMapping("/register")
    public CustomerRegisterResponseDTO register(@RequestBody CustomerRegisterReqDTO customerRegisterReqDTO){
        return customerService.register(customerRegisterReqDTO);
    }

    @PostMapping("/login")
    public CustomerLoginResponseDTO login(@RequestBody CustomerLoginReqDTO customerLoginReqDTO){
        return customerService.login(customerLoginReqDTO);
    }

    @PostMapping("/buy-tickets")
    public String buyTickets(@RequestBody BuyTicketReqDTO buyTicketReqDTO){
        return customerService.buyTickets(buyTicketReqDTO);
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestBody CustomerDeleteReqDTO customerDeleteReqDTO){
        return customerService.deleteCustomer(customerDeleteReqDTO);
    }

}
