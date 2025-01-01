package com.bookyourflight.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates IDs
    private String id;
    private String source;
    private String destination;

    private Date flightTime;
    private Integer passengers;

    @ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key to User table
    @JsonBackReference
    private User user;
}
