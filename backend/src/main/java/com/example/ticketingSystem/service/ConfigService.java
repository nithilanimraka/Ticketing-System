package com.example.ticketingSystem.service;

import com.example.ticketingSystem.dto.configuration.AddConfigReqDTO;
import com.example.ticketingSystem.dto.configuration.AddConfigResponseDTO;
import com.example.ticketingSystem.dto.configuration.GetConfigDTO;
import com.example.ticketingSystem.entity.Configuration;
import com.example.ticketingSystem.entity.TicketPool;
import com.example.ticketingSystem.repository.ConfigurationRepository;
import com.example.ticketingSystem.repository.TicketPoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ConfigService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    /**
     * Method to add a configuration to a ticketing system
     * @param addConfigReqDTO
     * @return
     */
    public AddConfigResponseDTO addConfigs(AddConfigReqDTO addConfigReqDTO) {
        Configuration configuration = new Configuration();
        AddConfigResponseDTO responseAddConfig = new AddConfigResponseDTO();
        try{
            configuration.setEventName(addConfigReqDTO.getEventName());
            configuration.setLocation(addConfigReqDTO.getLocation());
            configuration.setNo_of_tickets(addConfigReqDTO.getNo_of_tickets());
            configuration.setTicket_release_rate(addConfigReqDTO.getTicket_release_rate());
            configuration.setCustomer_retrieval_rate(addConfigReqDTO.getCustomer_retrieval_rate());
            configuration.setMax_tickets(addConfigReqDTO.getMax_tickets());
            configuration.setCurrentTicketCount(0);
            configurationRepository.save(configuration);

            TicketPool ticketPool = new TicketPool();
            ticketPool.setConfiguration(configuration);
            ticketPool.setMaxCapacity(addConfigReqDTO.getMax_tickets());
            ticketPoolRepository.save(ticketPool);

            responseAddConfig.setMessage("Configurations and TicketPool were successfully added to the system.");
            responseAddConfig.setStatus(true);
            return responseAddConfig;

        } catch (Exception e){
            log.error("Error in saving configurations to the system : {}", e.getMessage());
            responseAddConfig.setMessage("Configurations were not successfully added to the system.\n" + e.getMessage());
            responseAddConfig.setStatus(false);
            return responseAddConfig;
        }
    }

    /**
     * Method to get all configurations that are available in the system
     * @return List<GetConfigDTO>
     */
    public List<GetConfigDTO> getAllConfigs() {

        List<Configuration> configurations = configurationRepository.findAll();
        List<GetConfigDTO> getConfigDTOList = new ArrayList<>();
        for(Configuration configuration : configurations){
            GetConfigDTO getConfigDTO = new GetConfigDTO();
            getConfigDTO.setConfig_id(configuration.getConfig_id());
            getConfigDTO.setEventName(configuration.getEventName());
            getConfigDTO.setLocation(configuration.getLocation());
            getConfigDTO.setCurrentTicketCount(configuration.getCurrentTicketCount());
            getConfigDTOList.add(getConfigDTO);
        }

        return getConfigDTOList;

    }

    /**
     * Method to get a configuration by its id
     * @param id
     * @return GetConfigDTO
     */
    public GetConfigDTO getConfigById(Long id) {
        log.info("Get config by ID is running now: {}",id);
        Configuration configuration = configurationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuration not found with id: " + id));
        GetConfigDTO getConfigDTO = new GetConfigDTO();
        getConfigDTO.setConfig_id(configuration.getConfig_id());
        getConfigDTO.setEventName(configuration.getEventName());
        getConfigDTO.setLocation(configuration.getLocation());
        getConfigDTO.setCurrentTicketCount(configuration.getCurrentTicketCount());

        return getConfigDTO;
    }

}