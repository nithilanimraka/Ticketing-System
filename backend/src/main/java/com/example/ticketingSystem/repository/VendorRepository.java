package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByUsername(String username);
}