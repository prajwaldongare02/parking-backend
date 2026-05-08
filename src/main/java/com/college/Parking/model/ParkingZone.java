package com.college.Parking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "parking_zone")
public class ParkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;
    private String vehicleType;
    private int totalSlots;

    // 🔥 ADD THESE MANUALLY

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getZoneName() { return zoneName; }

    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getVehicleType() { return vehicleType; }

    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public int getTotalSlots() { return totalSlots; }

    public void setTotalSlots(int totalSlots) { this.totalSlots = totalSlots; }
}