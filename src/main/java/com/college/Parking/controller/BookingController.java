package com.college.Parking.controller;

import com.college.Parking.model.Booking;
import com.college.Parking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long userId,
                                           @RequestParam String vehicleNumber,
                                           @RequestParam String vehicleType,
                                           @RequestParam(required = false) String password) {

        try {

            Booking booking = bookingService.createBooking(
                    userId,
                    vehicleNumber,
                    vehicleType,
                    password
            );

            return ResponseEntity.ok(booking);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @PutMapping("/exit/{bookingId}")
    public Booking exitBooking(@PathVariable("bookingId") Long bookingId) {
        return bookingService.exitBooking(bookingId);
    }
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }
    @GetMapping("/active/{userId}")
    public Booking getActiveBooking(@PathVariable Long userId) {
        return bookingService.getActiveBooking(userId);
    }
    @GetMapping("/history/{userId}")
    public List<Booking> getUserBookingHistory(@PathVariable Long userId) {
        return bookingService.getUserBookingHistory(userId);
    }
}