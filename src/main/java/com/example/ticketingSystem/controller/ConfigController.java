package com.example.ticketingSystem.controller;

import com.example.ticketingSystem.dto.configuration.AddConfigReqDTO;
import com.example.ticketingSystem.dto.configuration.AddConfigResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ticketingSystem.service.ConfigService;

@RestController
@RequestMapping("/api/config")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConfigController {
    private ConfigService configService;

    @PostMapping("/add")
    public AddConfigResponseDTO addConfigurations(@RequestBody AddConfigReqDTO addConfigReqDTO){
        return configService.addConfigs(addConfigReqDTO);
    }
}
