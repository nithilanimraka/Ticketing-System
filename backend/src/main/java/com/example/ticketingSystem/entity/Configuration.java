package com.example.ticketingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONFIG_DETAILS")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private int no_of_tickets;
    private int ticket_release_rate;
    private int customer_retrieval_rate;
    private int max_tickets;
}
