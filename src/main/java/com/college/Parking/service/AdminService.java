package com.college.Parking.service;

import com.college.Parking.model.Booking;
import com.college.Parking.model.User;
import com.college.Parking.repository.BookingRepository;
import com.college.Parking.repository.ParkingSlotRepository;
import com.college.Parking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<Booking> getActiveBookingList() {
        return bookingRepository.findByStatus("ACTIVE");
    }

    // 1. Total slots
    public long getTotalSlots() {
        return slotRepository.count();
    }

    // 2. Available slots
    public long getAvailableSlots() {
        return slotRepository.countByStatus("AVAILABLE");
    }

    // 3. Occupied slots
    public long getOccupiedSlots() {
        return slotRepository.countByStatus("OCCUPIED");
    }

    // 4. Total bookings
    public long getTotalBookings() {
        return bookingRepository.count();
    }

    // 5. Active bookings
    public long getActiveBookings() {
        return bookingRepository.countByStatus("ACTIVE");
    }
    public double getTotalRevenue() {

        List<Booking> completedBookings = bookingRepository.findByStatus("COMPLETED");

        double revenue = 0;

        for (Booking booking : completedBookings) {
            if (booking.getTotalAmount() != null) {
                revenue += booking.getTotalAmount();
            }
        }

        return revenue;
    }
    public List<Booking> getActiveParking() {
        return bookingRepository.findByStatus("ACTIVE");
    }
    public double getTodayRevenue() {

        List<Booking> completedBookings = bookingRepository.findByStatus("COMPLETED");

        double revenue = 0;

        for (Booking booking : completedBookings) {

            if (booking.getExitTime() != null &&
                    booking.getExitTime().toLocalDate().equals(java.time.LocalDate.now())) {

                if (booking.getTotalAmount() != null) {
                    revenue += booking.getTotalAmount();
                }
            }
        }

        return revenue;
    }
}