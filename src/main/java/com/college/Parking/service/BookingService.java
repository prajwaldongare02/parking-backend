package com.college.Parking.service;

import com.college.Parking.model.*;
import com.college.Parking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // ================= CREATE BOOKING =================
    public Booking createBooking(Long userId,
                                 String vehicleNumber,
                                 String vehicleType,
                                 String password) {

        // 1️⃣ Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Check ACTIVE booking
        Optional<Booking> existingBooking =
                bookingRepository.findByUser_IdAndStatus(userId, "ACTIVE");

        if (existingBooking.isPresent()) {
            throw new RuntimeException("User already has an active booking");
        }

        // 3️⃣ Check if vehicle exists
        Optional<Vehicle> existingVehicle =
                vehicleRepository.findByVehicleNumberAndUserId(vehicleNumber, userId);

        // 4️⃣ If NEW vehicle → require password
        if (existingVehicle.isEmpty()) {

            if (password == null || !user.getPassword().equals(password)) {
                throw new RuntimeException("Password required for new vehicle");
            }

            // Save new vehicle
            Vehicle newVehicle = new Vehicle();
            newVehicle.setVehicleNumber(vehicleNumber);
            newVehicle.setVehicleType(vehicleType);
            newVehicle.setUser(user);

            vehicleRepository.save(newVehicle);
        }

        // 5️⃣ Find available slot based on vehicle type
        ParkingSlot slot = slotRepository
                .findFirstByStatusAndZone_VehicleType("AVAILABLE", vehicleType)
                .orElseThrow(() -> new RuntimeException("Parking Full"));

        // 6️⃣ Occupy slot
        slot.setStatus("OCCUPIED");
        slotRepository.save(slot);

        // 7️⃣ Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setVehicleType(vehicleType);
        booking.setEntryTime(LocalDateTime.now());
        booking.setStatus("ACTIVE");

        return bookingRepository.save(booking);
    }

    // ================= EXIT BOOKING =================
    public Booking exitBooking(Long bookingId) {

        // 1️⃣ Find booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2️⃣ Check already completed
        if ("COMPLETED".equals(booking.getStatus())) {
            throw new RuntimeException("Booking already completed");
        }

        // 3️⃣ Set exit time
        booking.setExitTime(LocalDateTime.now());

        // 4️⃣ Calculate duration
        long hours = java.time.Duration
                .between(booking.getEntryTime(), booking.getExitTime())
                .toHours();

        if (hours <= 0) {
            hours = 1;
        }

        // 5️⃣ Calculate amount
        double totalAmount = hours * 50;
        booking.setTotalAmount(totalAmount);

        // 6️⃣ Update status
        booking.setStatus("COMPLETED");

        // 7️⃣ Free slot
        ParkingSlot slot = booking.getSlot();
        slot.setStatus("AVAILABLE");
        slotRepository.save(slot);

        // 8️⃣ Save booking
        return bookingRepository.save(booking);
    }

    // ================= GET ALL BOOKINGS =================
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // ================= GET BOOKINGS BY USER =================
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }

    // ================= GET ACTIVE BOOKING =================
    public Booking getActiveBooking(Long userId) {

        Optional<Booking> booking =
                bookingRepository.findByUser_IdAndStatus(userId, "ACTIVE");

        return booking.orElse(null);
    }

    // ================= BOOKING HISTORY =================
    public List<Booking> getUserBookingHistory(Long userId) {
        return bookingRepository.findByUser_Id(userId);
    }
}

