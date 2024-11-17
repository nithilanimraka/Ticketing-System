package com.example.ticketingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pool_id;

    private int maxCapacity;

}
