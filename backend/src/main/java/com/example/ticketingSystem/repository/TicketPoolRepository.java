package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}
