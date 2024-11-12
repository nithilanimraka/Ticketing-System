package com.example.ticketingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Long id;
    private String name;
    private String email;
    private String userName;
    private String password;
    private int tickets_bought;
}