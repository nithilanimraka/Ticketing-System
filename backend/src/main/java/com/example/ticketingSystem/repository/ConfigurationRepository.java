package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.Configuration;
import com.example.ticketingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Optional<Configuration> findByEventName(String eventName);
}