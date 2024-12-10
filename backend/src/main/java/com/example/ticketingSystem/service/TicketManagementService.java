package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * This class manages the ticket functionalities. Reduces complexities and eliminates the circular error which was
 * there in runtime.
 */
@Service
public class TicketManagementService {

    private final TicketPoolService ticketPoolService;

    @Autowired
    public TicketManagementService( TicketPoolService ticketPoolService) {

        this.ticketPoolService = ticketPoolService;
    }

    /**
     * Method to add tickets by a vendor. This method is called in the VendorService class
     * @param configId
     * @param count
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Boolean addTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        return ticketPoolService.addTickets(count, configId);
    }

    /**
     * Method to buy tickets by a customer. This method is called in the CustomerService class
     * @param configId
     * @param count
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Boolean removeTickets(Long configId, int count) throws InterruptedException, ExecutionException {
        return ticketPoolService.removeTickets(count, configId);
    }

}