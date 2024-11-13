package com.example.ticketingSystem.service;

import lombok.extern.slf4j.Slf4j;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class TicketPool {
    private final List<Integer> tickets = Collections.synchronizedList(new LinkedList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void addTickets(int count)throws InterruptedException {
        synchronized (tickets){
            if (tickets.size() + count <= maxCapacity) {
                for (int i = 0; i < count; i++) {
                    if(tickets.size() < 50){
                        tickets.add(1); // Add a ticket
                        log.info("Added 1 ticket. Total: {}", tickets.size());
                    } else {
                        log.info("Ticket Capacity is insufficient");
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Thread was interrupted", e);
                    }
                }
            } else {
                int remainingTicketSlots = maxCapacity - tickets.size();
                log.info("Adding {} tickets will exceed maximum ticket count",count);
                log.info("Remaining ticket slots: {}", remainingTicketSlots);
            }
        }
    }

    public void removeTickets(int count) throws InterruptedException{
        synchronized (tickets){
            if (tickets.size() >= count) {
                for (int i = 0; i < count; i++) {
                    tickets.removeFirst();
                    log.info("Removed 1 ticket. Total: {}", tickets.size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Thread was interrupted", e);
                    }
                }
            } else {
                int remainingTickets = tickets.size();
                log.info("The number you requested exceeds the number of available tickets");
                log.info("Available tickets: " + remainingTickets);
            }
        }
    }
}
