package com.example.ticketingSystem.controller;

import com.example.ticketingSystem.dto.configuration.AddConfigReqDTO;
import com.example.ticketingSystem.dto.configuration.AddConfigResponseDTO;
import com.example.ticketingSystem.dto.configuration.GetConfigDTO;
import com.example.ticketingSystem.entity.Configuration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ticketingSystem.service.ConfigService;

import java.util.List;

@RestController
@RequestMapping("/api/config")
@CrossOrigin
@Slf4j
public class ConfigController {
    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/add")
    public AddConfigResponseDTO addConfigurations(@RequestBody AddConfigReqDTO addConfigReqDTO){
        return configService.addConfigs(addConfigReqDTO);
    }

    @GetMapping("/all")
    public List<GetConfigDTO> getAllConfigurations() {
        return configService.getAllConfigs();
    }
}
