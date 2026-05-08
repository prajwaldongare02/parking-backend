package com.college.Parking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "parking_slot")
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotNumber;   // Example: A1, A2

    private String status;       // AVAILABLE / OCCUPIED / RESERVED
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ParkingZone getZone() {
        return zone;
    }

    public void setZone(ParkingZone zone) {
        this.zone = zone;
    }


    @ManyToOne
    @JoinColumn(name = "zone_id")
    private ParkingZone zone;
}
