package com.example.ticketingSystem.service;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class TicketPool {
    private final List<Integer> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;
    private final ExecutorService executorService;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.executorService = Executors.newCachedThreadPool();

    }

    public Boolean addTickets(int count)throws InterruptedException,ExecutionException {
        Future<Boolean> future = executorService.submit(() -> {
            synchronized (this) {
                if (tickets.size() + count <= maxCapacity) {
                    for (int i = 0; i < count; i++) {
                        if (tickets.size() < 50) {
                            tickets.add(1); // Add a ticket
                            log.info("Added 1 ticket. Total: {}", tickets.size());
                        } else {
                            log.info("Ticket Capacity is insufficient");
                            return false;
                        }
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
                    int remainingTicketSlots = maxCapacity - tickets.size();
                    log.info("Adding {} tickets will exceed maximum ticket count", count);
                    log.info("Remaining ticket slots: {}", remainingTicketSlots);
                    return false;
                }
            }
        });
        return future.get();
    }

    public Boolean removeTickets(int count) throws InterruptedException, ExecutionException {
    Future<Boolean> future = executorService.submit(() -> {
        synchronized (this) {
            if (tickets.size() >= count) {
                for (int i = 0; i < count; i++) {
                    tickets.remove(0);
                    log.info("Removed 1 ticket. Total: {}", tickets.size());
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
                int remainingTickets = tickets.size();
                log.info("The number you requested exceeds the number of available tickets");
                log.info("Available tickets: " + remainingTickets);
                return false;
            }
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
