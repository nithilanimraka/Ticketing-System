package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.Configuration;
import com.example.ticketingSystem.repository.ConfigurationRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketPoolService {

    @Autowired
    private final ConfigurationRepository configurationRepository;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Lock addLock = new ReentrantLock();
    private final Lock removeLock = new ReentrantLock();


    public Boolean addTickets(int count, Long id)throws InterruptedException,ExecutionException {
        Configuration configNew = configurationRepository.findById(id).orElseThrow();
        int maxTickets = configNew.getMax_tickets();
        Future<Boolean> future = executorService.submit(() -> {
            addLock.lock();
            try{

                if (configNew.getCurrentTicketCount() + count <= maxTickets) {
                    for (int i = 0; i < count; i++) {
                        Configuration configuration = configurationRepository.findById(id).orElseThrow();
                        configuration.setCurrentTicketCount(configuration.getCurrentTicketCount() +1);
                        Configuration savedConfig = configurationRepository.save(configuration);
                        log.info("Added 1 ticket. Total: {}", savedConfig.getCurrentTicketCount());

                        try {
                            Thread.sleep(1000);
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

    public Boolean removeTickets(int count, Long id) throws InterruptedException, ExecutionException {

        Configuration configNew = configurationRepository.findById(id).orElseThrow();
        int ticketCount = configNew.getCurrentTicketCount();

        Future<Boolean> future = executorService.submit(() -> {
            removeLock.lock();
            try{

                if (ticketCount >= count) {
                    for (int i = 0; i < count; i++) {
                        Configuration configuration = configurationRepository.findById(id).orElseThrow();
                        configuration.setCurrentTicketCount(configuration.getCurrentTicketCount() -1);
                        Configuration savedConfig = configurationRepository.save(configuration);
                        log.info("Removed 1 ticket. Total: {}", savedConfig.getCurrentTicketCount());
                        try {
                            Thread.sleep(1000);
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
