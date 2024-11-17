package com.example.ticketingSystem.service;

import com.example.ticketingSystem.entity.Ticket;
import com.example.ticketingSystem.entity.TicketPool;
import com.example.ticketingSystem.repository.TicketPoolRepository;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class TicketPoolService {
//    @Autowired
//    private TicketPoolRepository ticketPoolRepository;

    private final TicketPoolRepository ticketPoolRepository;
//    private final ConfigService configService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Lock addLock = new ReentrantLock();
    private final Lock removeLock = new ReentrantLock();


    @Autowired
    public TicketPoolService(TicketPoolRepository ticketPoolRepository) {
        this.ticketPoolRepository = ticketPoolRepository;
//        this.configService = configService;
    }

    //get ticketpool ID from foreign key of configuration and set add ticket remove ticket to that ticketpool
//    @Autowired
//    private ConfigService configService;
//
//    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public TicketPool createTicketPool(int maxCapacity) {
        TicketPool ticketPool = new TicketPool();
        ticketPool.setMaxCapacity(maxCapacity);
        ticketPool.setTickets(Collections.synchronizedList(new LinkedList<>()));
        return ticketPoolRepository.save(ticketPool);
    }
//    private final List<Integer> tickets = Collections.synchronizedList(new LinkedList<>());
//    private final int maxCapacity;
//    private final ExecutorService executorService;
//
//    public TicketPoolService(int maxCapacity) {
//        this.maxCapacity = maxCapacity;
//        this.executorService = Executors.newCachedThreadPool();
//
//    }

    public Boolean addTickets(int count, TicketPool ticketPool)throws InterruptedException,ExecutionException {

//        TicketPool ticketPool = configService.getTicketPoolByConfigId(configId);
        Future<Boolean> future = executorService.submit(() -> {
            addLock.lock();
            try {
                for (int i = 0; i < count; i++) {
                    TicketPool latestTicketPool = ticketPoolRepository.findByIdWithTickets(ticketPool.getPool_id());
                    List<Ticket> tickets = latestTicketPool.getTickets();
                    if (tickets.size() + 1 <= latestTicketPool.getMaxCapacity()) {
                        tickets.add(new Ticket(null, "Ticket details")); // Add a new ticket
                        latestTicketPool.setTickets(tickets);
                        ticketPoolRepository.save(latestTicketPool);
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
            } finally {
                addLock.unlock();
            }
        });
        return future.get();
    }


//        Future<Boolean> future = executorService.submit(() -> {
//            synchronized (this) {
//                if (tickets.size() + count <= maxCapacity) {
//                    for (int i = 0; i < count; i++) {
//                        if (tickets.size() < 50) {
//                            tickets.add(1); // Add a ticket
//                            log.info("Added 1 ticket. Total: {}", tickets.size());
//                        } else {
//                            log.info("Ticket Capacity is insufficient");
//                            return false;
//                        }
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            Thread.currentThread().interrupt();
//                            log.error("Thread was interrupted", e);
//                            return false;
//                        }
//                    }
//                    return true;
//                } else {
//                    int remainingTicketSlots = maxCapacity - tickets.size();
//                    log.info("Adding {} tickets will exceed maximum ticket count", count);
//                    log.info("Remaining ticket slots: {}", remainingTicketSlots);
//                    return false;
//                }
//            }
//        });
//        return future.get();
//    }

    public Boolean removeTickets(int count,TicketPool ticketPool) throws InterruptedException, ExecutionException {

//        TicketPool ticketPool = configService.getTicketPoolByConfigId(configId);
        Future<Boolean> future = executorService.submit(() -> {
            removeLock.lock();
            try {
                for (int i = 0; i < count; i++) {
                    TicketPool latestTicketPool = ticketPoolRepository.findByIdWithTickets(ticketPool.getPool_id());
                    List<Ticket> tickets = latestTicketPool.getTickets();
                    if (!tickets.isEmpty()) {
                        tickets.remove(0); // Remove a ticket
                        latestTicketPool.setTickets(tickets);
                        ticketPoolRepository.save(latestTicketPool);
                        log.info("Removed 1 ticket. Total: {}", tickets.size());
                    } else {
                        log.info("The number you requested exceeds the number of available tickets");
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
            } finally {
                removeLock.unlock();
            }
        });
        return future.get();
    }

//    Future<Boolean> future = executorService.submit(() -> {
//        synchronized (this) {
//            if (tickets.size() >= count) {
//                for (int i = 0; i < count; i++) {
//                    tickets.remove(0);
//                    log.info("Removed 1 ticket. Total: {}", tickets.size());
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                        log.error("Thread was interrupted", e);
//                        return false;
//                    }
//                }
//                return true;
//            } else {
//                int remainingTickets = tickets.size();
//                log.info("The number you requested exceeds the number of available tickets");
//                log.info("Available tickets: " + remainingTickets);
//                return false;
//            }
//        }
//    });
//    return future.get();
//}

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
