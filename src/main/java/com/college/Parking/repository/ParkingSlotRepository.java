package com.college.Parking.repository;
import com.college.Parking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {

    Optional<ParkingSlot> findFirstByStatusAndZone_VehicleType(String status, String vehicleType);
    long countByStatus(String status);
    List<ParkingSlot> findByStatus(String status);
    Optional<ParkingSlot> findBySlotNumber(String slotNumber);
    long countByZone_Id(Long zoneId);
}
