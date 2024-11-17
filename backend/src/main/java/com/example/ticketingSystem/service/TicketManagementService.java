package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TicketManagementService {

    private final ConfigService configService;
    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketManagementService(ConfigService configService, TicketPoolService ticketPoolService) {
        this.configService = configService;
        this.ticketPoolService = ticketPoolService;
    }

    public Boolean addTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        TicketPool ticketPool = configService.getTicketPoolByConfigId(configId);
        return ticketPoolService.addTickets(count, ticketPool);
    }

    public Boolean removeTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        TicketPool ticketPool = configService.getTicketPoolByConfigId(configId);
        return ticketPoolService.removeTickets(count, ticketPool);
    }

    public TicketPool createTicketPool(int maxCapacity) {
        return ticketPoolService.createTicketPool(maxCapacity);
    }
}