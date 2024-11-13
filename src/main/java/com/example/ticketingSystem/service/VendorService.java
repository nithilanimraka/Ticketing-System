package com.example.ticketingSystem.service;

import com.example.ticketingSystem.dto.vendor.*;
import lombok.extern.slf4j.Slf4j;
import com.example.ticketingSystem.repository.VendorRepository;
import com.example.ticketingSystem.entity.Vendor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorRegisterResponseDTO register(VendorRegisterReqDTO vendorLoginReqDTO){
        Vendor vendor = new Vendor();
        VendorRegisterResponseDTO responseRegister = new VendorRegisterResponseDTO();
        try{
            vendor.setName(vendorLoginReqDTO.getName());
            vendor.setEmail(vendorLoginReqDTO.getEmail());
            vendor.setUserName(vendorLoginReqDTO.getUsername());
            vendor.setPassword(vendorLoginReqDTO.getPassword());
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
            Vendor vendor = vendorRepository.findById(vendorLoginReqDTO.getId()).orElseThrow(null);
            String usernameStored = vendor.getUserName();
            String passwordStored = vendor.getPassword();
            if(usernameStored.equals(vendorLoginReqDTO.getUsername())
                    && passwordStored.equals(vendorLoginReqDTO.getPassword())){
                responseLogin.setMessage("Login successful");
                log.info("Vendor logged in successfully");
            } else {
                responseLogin.setMessage("The credentials that have entered are incorrect");
                log.info("Vendor login failed due to invalid credentials");
            }
            return responseLogin;
        } catch (Exception e){
            log.info("Login was unsuccessful : " + e.getMessage());
            responseLogin.setMessage("Error occurred while login");
            return responseLogin;
        }
    }

    public String deleteVendor(VendorDeleteReqDTO vendorDeleteReqDTO){
        Vendor vendor = vendorRepository.findById(vendorDeleteReqDTO.getId()).orElseThrow(null);
        vendorRepository.delete(vendor);
        log.info("Vendor with id " + vendorDeleteReqDTO.getId() + " was deleted successfully");
        return "Vendor was deleted successfully";
    }
}
