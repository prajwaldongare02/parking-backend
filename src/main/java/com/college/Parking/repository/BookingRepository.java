package com.college.Parking.repository;

import com.college.Parking.model.Booking;
import com.college.Parking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Get all bookings of a user
    List<Booking> findByUser_Id(Long userId);

    // Get ACTIVE booking of a user
    Optional<Booking> findByUser_IdAndStatus(Long userId, String status);
    long countByStatus(String status);
    List<Booking> findByStatus(String status);


}