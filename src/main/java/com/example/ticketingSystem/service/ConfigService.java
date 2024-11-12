package com.example.ticketingSystem.service;

import com.example.ticketingSystem.dto.configuration.AddConfigReqDTO;
import com.example.ticketingSystem.dto.configuration.AddConfigResponseDTO;
import com.example.ticketingSystem.entity.Configuration;
import com.example.ticketingSystem.repository.ConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigService {
    @Autowired
    private ConfigurationRepository configurationRepository;

    public AddConfigResponseDTO addConfigs(AddConfigReqDTO addConfigReqDTO) {
        Configuration configuration = new Configuration();
        AddConfigResponseDTO responseAddConfig = new AddConfigResponseDTO();
        try {
            configuration.setNo_of_tickets(addConfigReqDTO.getNo_of_tickets());
            configuration.setTicket_release_rate(addConfigReqDTO.getTicket_release_rate());
            configuration.setCustomer_retrieval_rate(addConfigReqDTO.getCustomer_retrieval_rate());
            configuration.setMax_tickets(addConfigReqDTO.getMax_tickets());
            configurationRepository.save(configuration);
            log.info("configurations were added to the system");
            responseAddConfig.setId(configuration.getId());
            responseAddConfig.setMessage("Configurations were successfully added to the system.");
            return responseAddConfig;
        } catch (Exception e) {
            log.error("Error in saving configurations to the system : " + e.getMessage());
            responseAddConfig.setMessage("Configurations were not successfully added to the system.\n" + e.getMessage());
            return responseAddConfig;
        }
    }
}