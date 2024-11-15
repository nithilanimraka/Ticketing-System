package com.example.ticketingSystem.service;

import com.example.ticketingSystem.dto.vendor.*;
import com.example.ticketingSystem.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import com.example.ticketingSystem.repository.VendorRepository;
import com.example.ticketingSystem.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public VendorRegisterResponseDTO register(VendorRegisterReqDTO vendorRegisterReqDTO){
        Vendor vendor = new Vendor();
        VendorRegisterResponseDTO responseRegister = new VendorRegisterResponseDTO();
        try{
            vendor.setName(vendorRegisterReqDTO.getName());
            vendor.setEmail(vendorRegisterReqDTO.getEmail());
            vendor.setUsername(vendorRegisterReqDTO.getUsername());
            vendor.setPassword(this.passwordEncoder.encode(vendorRegisterReqDTO.getPassword()));
            vendor.setTickets_added(0);
            vendorRepository.save(vendor);
            log.info("Vendor saved in database");
            responseRegister.setMessage("Registration was successful with id : " + vendor.getId());
            return responseRegister;
        } catch (Exception e){
            log.error("Vendor saving unsuccessful : " + e.getMessage());
            responseRegister.setMessage("Registration was not successful");
            return responseRegister;
        }
    }

    public VendorLoginResponseDTO login(VendorLoginReqDTO vendorLoginReqDTO){
        VendorLoginResponseDTO responseLogin = new VendorLoginResponseDTO();
        try{

            Vendor vendor = vendorRepository.findByUsername(vendorLoginReqDTO.getUsername());
            if(vendor != null){
                String password = vendorLoginReqDTO.getPassword();
                String encodedPassword = vendor.getPassword();
                boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
                if(isPwdRight){
                    Optional<Vendor> vendor1 = vendorRepository.findOneByUsernameAndPassword(vendorLoginReqDTO.getUsername(), encodedPassword);
                    if(vendor1.isPresent()){
                        responseLogin.setMessage("Login successful");
                        responseLogin.setStatus(true);
                    }else {
                        responseLogin.setMessage("The credentials that have been entered are incorrect");
                        responseLogin.setStatus(false);
                    }
                }else {
                    responseLogin.setMessage("Password is incorrect");
                    responseLogin.setStatus(false);
                }

            }else {
                responseLogin.setMessage("Username does not exist");
                responseLogin.setStatus(false);
            }

//            Vendor vendor = vendorRepository.findByUsername(vendorLoginReqDTO.getUsername());
//            String usernameStored = vendor.getUsername();
//            String passwordStored = vendor.getPassword();
//            if(usernameStored.equals(vendorLoginReqDTO.getUsername())
//                    && passwordStored.equals(vendorLoginReqDTO.getPassword())){
//                responseLogin.setMessage("Login successful");
//                log.info("Vendor logged in successfully");
//            } else {
//                responseLogin.setMessage("The credentials that have entered are incorrect");
//                log.info("Vendor login failed due to invalid credentials");
//            }
            return responseLogin;
        } catch (Exception e){
            log.info("Login was unsuccessful : " + e.getMessage());
            responseLogin.setMessage("Error occurred while login");
            responseLogin.setStatus(false);
            return responseLogin;
        }
    }

    public String deleteVendor(VendorDeleteReqDTO vendorDeleteReqDTO){
        Vendor vendor = vendorRepository.findById(vendorDeleteReqDTO.getId()).orElseThrow(null);
        vendorRepository.delete(vendor);
        log.info("Vendor with id " + vendorDeleteReqDTO.getId() + " was deleted successfully");
        return "Vendor was deleted successfully";
    }

    public String addTickets(AddTicketReqDTO addTicketReqDTO){
        int tickets = addTicketReqDTO.getRequest_tickets_add();
        TicketPool ticketPool = new TicketPool(50);
        try{
            ticketPool.addTickets(tickets);
            return "Tickets added successfully";
        } catch (Exception e){
            log.info("Ticket addition was not successful : " + e.getMessage());
            return "An error occurred : " + e.getMessage();
        }
    }

}
