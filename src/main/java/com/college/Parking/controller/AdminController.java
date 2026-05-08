package com.college.Parking.controller;

import com.college.Parking.model.Booking;
import com.college.Parking.model.User;
import com.college.Parking.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/total-slots")
    public long totalSlots() {
        return adminService.getTotalSlots();
    }

    @GetMapping("/available-slots")
    public long availableSlots() {
        return adminService.getAvailableSlots();
    }

    @GetMapping("/occupied-slots")
    public long occupiedSlots() {
        return adminService.getOccupiedSlots();
    }

    @GetMapping("/total-bookings")
    public long totalBookings() {
        return adminService.getTotalBookings();
    }

    @GetMapping("/active-bookings")
    public long activeBookings() {
        return adminService.getActiveBookings();
    }
    @GetMapping("/active-booking-list")
    public List<Booking> getActiveBookingList() {
        return adminService.getActiveBookingList(); 
    }
    @GetMapping("/all-users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalSlots", adminService.getTotalSlots());
        dashboard.put("availableSlots", adminService.getAvailableSlots());
        dashboard.put("occupiedSlots", adminService.getOccupiedSlots());
        dashboard.put("totalBookings", adminService.getTotalBookings());
        dashboard.put("activeBookings", adminService.getActiveBookings());
        dashboard.put("totalUsers", adminService.getAllUsers().size());

        return dashboard;
    }
    @GetMapping("/revenue")
    public double getTotalRevenue() {
        return adminService.getTotalRevenue();
    }
    @GetMapping("/active-parking")
    public List<Booking> getActiveParking() {
        return adminService.getActiveParking();
    }
    @GetMapping("/today-revenue")
    public double getTodayRevenue() {
        return adminService.getTodayRevenue();
    }
}