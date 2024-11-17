package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    @Query("SELECT tp FROM TicketPool tp LEFT JOIN FETCH tp.tickets WHERE tp.pool_id = :id")
    TicketPool findByIdWithTickets(Long id);
}
