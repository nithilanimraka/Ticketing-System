package com.example.ticketingSystem.repository;

import com.example.ticketingSystem.entity.Customer;
import com.example.ticketingSystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findOneByUsernameAndPassword(String username, String password);

    Vendor findByUsername(String username);
}