package com.example.ticketingSystem.service;

import com.example.ticketingSystem.dto.customer.*;
import com.example.ticketingSystem.entity.Customer;
import com.example.ticketingSystem.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerService implements Runnable {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerRegisterResponseDTO register(CustomerRegisterReqDTO customerLoginReqDTO){
        Customer customer = new Customer();
        CustomerRegisterResponseDTO responseRegister = new CustomerRegisterResponseDTO();
        try{
            customer.setName(customerLoginReqDTO.getName());
            customer.setEmail(customerLoginReqDTO.getEmail());
            customer.setUserName(customerLoginReqDTO.getUsername());
            customer.setPassword(customerLoginReqDTO.getPassword());
            customer.setTickets_bought(0);
            customerRepository.save(customer);
            log.info("Customer saved in database");
            responseRegister.setMessage("Registration was successful with id : " + customer.getId());
            return responseRegister;
        } catch (Exception e){
            log.error("Customer saving unsuccessful : " + e.getMessage());
            responseRegister.setMessage("Registration was not successful");
            return responseRegister;
        }
    }

    public CustomerLoginResponseDTO login(CustomerLoginReqDTO customerLoginReqDTO){
        CustomerLoginResponseDTO responseLogin = new CustomerLoginResponseDTO();
        try{
            Customer customer = customerRepository.findById(customerLoginReqDTO.getId()).orElseThrow(null);
            String usernameStored = customer.getUserName();
            String passwordStored = customer.getPassword();
            if(usernameStored.equals(customerLoginReqDTO.getUsername())
                    && passwordStored.equals(customerLoginReqDTO.getPassword())){
                responseLogin.setMessage("Login successful");
                log.info("Customer logged in successfully");
            } else {
                responseLogin.setMessage("The credentials that have been entered are incorrect");
                log.info("Customer login failed due to invalid credentials");
            }
            return responseLogin;
        } catch (Exception e){
            log.info("Login was unsuccessful : " + e.getMessage());
            responseLogin.setMessage("Error occurred while login");
            return responseLogin;
        }
    }

    public String deleteCustomer(CustomerDeleteReqDTO customerDeleteReqDTO){
        Customer customer = customerRepository.findById(customerDeleteReqDTO.getId()).orElseThrow(null);
        customerRepository.delete(customer);
        log.info("Customer with id " + customerDeleteReqDTO.getId() + " was deleted successfully");
        return "Customer deleted";
    }

    public String buyTickets(BuyTicketReqDTO buyTicketReqDTO){
        int tickets = buyTicketReqDTO.getRequested_tickets_buy();
        TicketPool ticketPool = new TicketPool(50);
        try{
            ticketPool.removeTickets(tickets);
            log.info("Tickets bought successfully");
            return "Tickets bought successfully";
        } catch (Exception e){
            log.error("Error in buying tickets : " + e.getMessage());
            return "Error in buying tickets";
        }
    }

    @Override
    public void run() {

    }


}
