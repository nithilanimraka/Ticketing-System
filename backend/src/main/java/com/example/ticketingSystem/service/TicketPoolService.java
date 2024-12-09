package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.Configuration;
import com.example.ticketingSystem.entity.Ticket;
import com.example.ticketingSystem.entity.TicketPool;
import com.example.ticketingSystem.repository.ConfigurationRepository;
import com.example.ticketingSystem.repository.TicketPoolRepository;
import com.example.ticketingSystem.repository.TicketRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.*;  //For concurrent HashMap
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketPoolService {

    @Autowired
    private final ConfigurationRepository configurationRepository;
    @Autowired
    private final TicketPoolRepository ticketPoolRepository;
    @Autowired
    private final TicketRepository ticketRepository;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * A map of locks to synchronize access to the ticket pool unique to each configuration
     */
    private final ConcurrentHashMap<Long, Lock> addLockMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Lock> removeLockMap = new ConcurrentHashMap<>();


    /**
     * Method to add tickets by the vendor
     * @param count
     * @param configId
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Boolean addTickets(int count, Long configId)throws InterruptedException,ExecutionException {
        Configuration configNew = configurationRepository.findById(configId)
                .orElseThrow(()  -> new RuntimeException("Event not found for event Id: " + configId));
        Long id = configNew.getConfig_id();
        TicketPool ticketPool = ticketPoolRepository.findByConfiguration(configNew).orElseThrow();
        int maxTickets = configNew.getMax_tickets();
        int totalTickets = configNew.getNo_of_tickets();

        Lock addLock = addLockMap.computeIfAbsent(id, k -> new ReentrantLock());


        Future<Boolean> future = executorService.submit(() -> {
            addLock.lock();
            try{
                if(count > totalTickets){
                    log.info("The number of tickets you are trying to add exceeds the total number of tickets available: {}", totalTickets);
                    return false;
                }

                else if (configNew.getCurrentTicketCount() + count <= maxTickets) {
                    for (int i = 0; i < count; i++) {
                        Configuration configuration = configurationRepository.findById(id).orElseThrow();
                        configuration.setCurrentTicketCount(configuration.getCurrentTicketCount() +1);
                        configuration.setNo_of_tickets(configuration.getNo_of_tickets() -1);
                        configurationRepository.save(configuration);

                        Ticket ticket = new Ticket();
                        ticket.setTicketPool(ticketPool);
                        ticketRepository.save(ticket);

                        log.info("Added 1 ticket to config with id: {}. Total: {}", configuration.getConfig_id(), configuration.getCurrentTicketCount());

                        int waitTime = 1000/configNew.getTicket_release_rate();

                        try {
                            Thread.sleep(waitTime);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.error("Thread was interrupted", e);
                            return false;
                        }
                    }
                    return true;
                } else {
                    int remainingTicketSlots = configNew.getMax_tickets() - configNew.getCurrentTicketCount();
                    log.info("Adding {} tickets will exceed maximum ticket count", count);
                    log.info("Remaining ticket slots: {}", remainingTicketSlots);
                    return false;
                }

            } finally {
                addLock.unlock();
            }
        });
        return future.get();

    }

    /**
     * Method to buy tickets by the customer
     * @param count
     * @param configId
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Boolean removeTickets(int count, Long configId) throws InterruptedException, ExecutionException {

        Configuration configNew = configurationRepository.findById(configId)
                .orElseThrow(() -> new RuntimeException("Event not found for event Id: " + configId));
        Long id = configNew.getConfig_id();
        TicketPool ticketPool = ticketPoolRepository.findByConfiguration(configNew).orElseThrow();
        int ticketCount = configNew.getCurrentTicketCount();

        Lock removeLock = removeLockMap.computeIfAbsent(id, k -> new ReentrantLock());

        Future<Boolean> future = executorService.submit(() -> {
            removeLock.lock();
            try{

                if (ticketCount >= count) {
                    for (int i = 0; i < count; i++) {
                        Configuration configuration = configurationRepository.findById(id).orElseThrow();
                        configuration.setCurrentTicketCount(configuration.getCurrentTicketCount() -1);
                        configurationRepository.save(configuration);

                        Ticket ticket = ticketRepository.findFirstByTicketPoolOrderByTicketIdAsc(ticketPool).orElseThrow();
                        ticketRepository.delete(ticket);

                        log.info("Removed 1 ticket from config with id: {}. Total: {}",configuration.getConfig_id(), configuration.getCurrentTicketCount());

                        int waitTime = 1000/configNew.getCustomer_retrieval_rate();

                        try {
                            Thread.sleep(waitTime);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            log.error("Thread was interrupted", e);
                            return false;
                        }
                    }
                    return true;
                } else {
                    int remainingTickets =  configNew.getCurrentTicketCount();
                    log.info("The number you requested exceeds the number of available tickets");
                    log.info("Available tickets: " + remainingTickets);
                    return false;
                }

            } finally {
                removeLock.unlock();
            }
        });
        return future.get();
    }

    /**
     * Method to cleanly terminate a ExecutorService when a Spring-managed bean is about to be destroyed.
     */
    @PreDestroy
    public void shutdown() {
        log.info("Shutting down ExecutorService...");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
