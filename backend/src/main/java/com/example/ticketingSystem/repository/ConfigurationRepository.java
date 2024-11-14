package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}