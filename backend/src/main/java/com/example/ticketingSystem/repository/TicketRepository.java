package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.Ticket;
import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findFirstByTicketPoolOrderByTicketIdAsc(TicketPool ticketPool);
}