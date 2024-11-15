package com.example.ticketingSystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @Id
    private Long id;
    private String name;
    private String email;
    @Column(unique = true)
    private String username;

    private String password;
    private int tickets_added;
}
