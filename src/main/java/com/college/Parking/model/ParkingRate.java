package com.college.Parking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "parking_rate")
public class ParkingRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleType;   // CAR or BIKE

    private double ratePerHour;   // Example: 30.0
}
