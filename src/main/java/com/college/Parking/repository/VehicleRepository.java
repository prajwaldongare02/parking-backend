package com.college.Parking.repository;

import com.college.Parking.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByUser_Id(Long userId);

    Optional<Vehicle> findByVehicleNumberAndUserId(String vehicleNumber, Long userId);


}