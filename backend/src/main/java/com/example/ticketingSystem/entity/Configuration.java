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

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL)
    private List<Vendor> vendors;

//    @OneToMany(targetEntity =Vendor.class,cascade = CascadeType.ALL)
//    @JoinColumn(name = "fk_config_id", referencedColumnName = "config_id")
//    private List<Vendor> vendor;
}
