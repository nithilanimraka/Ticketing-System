package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class TicketManagementService {

    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketManagementService( TicketPoolService ticketPoolService) {
        this.ticketPoolService = ticketPoolService;
    }

    public Boolean addTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        return ticketPoolService.addTickets(count, configId);
    }

    public Boolean removeTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        return ticketPoolService.removeTickets(count, configId);
    }

}