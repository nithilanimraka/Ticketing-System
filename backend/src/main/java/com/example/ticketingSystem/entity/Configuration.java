package com.example.ticketingSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CONFIG_DETAILS")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long config_id;
    private String eventName;
    private String location;
    private int no_of_tickets;
    private int ticket_release_rate;
    private int customer_retrieval_rate;
    private int max_tickets;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vendor> vendors;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ticket_pool_id", referencedColumnName = "pool_id")
//    private TicketPool ticketPool;

    @OneToOne
    @JoinColumn(name= "ticket_pool_id", nullable = false)
    private TicketPool ticketPool;

}
