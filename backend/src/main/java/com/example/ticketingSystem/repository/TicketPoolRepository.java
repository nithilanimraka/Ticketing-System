package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.TicketPool;
import com.example.ticketingSystem.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
    Optional<TicketPool> findByConfiguration(Configuration configuration);
}