package com.college.Parking.repository;

import com.college.Parking.model.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingZoneRepository extends JpaRepository<ParkingZone, Long> {
    Optional<ParkingZone> findByZoneNameAndVehicleType(String zoneName, String vehicleType);
}