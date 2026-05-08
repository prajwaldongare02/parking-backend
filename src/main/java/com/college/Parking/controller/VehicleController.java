package com.college.Parking.controller;

import com.college.Parking.model.Vehicle;
import com.college.Parking.repository.VehicleRepository;


import com.college.Parking.model.User;
import com.college.Parking.model.Vehicle;
import com.college.Parking.repository.UserRepository;
import com.college.Parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public List<Vehicle> getVehicles(@PathVariable Long userId) {
        return vehicleRepository.findByUser_Id(userId);
    }

    @PostMapping("/add")
    public Vehicle addVehicle(@RequestParam Long userId,
                              @RequestParam String vehicleNumber,
                              @RequestParam String vehicleType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(vehicleType);
        vehicle.setUser(user);

        return vehicleRepository.save(vehicle);
    }


    @PostMapping("/add-new")
    public Vehicle addNewVehicleForUser(@RequestParam Long userId,
                                        @RequestParam String vehicleNumber,
                                        @RequestParam String vehicleType,
                                        @RequestParam String password) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        Optional<Vehicle> existingVehicle = vehicleRepository.findByVehicleNumberAndUserId(vehicleNumber, userId);

        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Vehicle already exists for this user");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(vehicleType);
        vehicle.setUser(user);

        return vehicleRepository.save(vehicle);
    }
    @PostMapping("/add-today-vehicle")
    public Vehicle addTodayVehicle(@RequestParam Long userId,
                                   @RequestParam String vehicleNumber,
                                   @RequestParam String vehicleType,
                                   @RequestParam String password) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Password verification
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid Password");
        }

        // Check if vehicle already exists
        Optional<Vehicle> existing =
                vehicleRepository.findByVehicleNumberAndUserId(vehicleNumber, userId);

        if (existing.isPresent()) {
            throw new RuntimeException("Vehicle already registered");
        }

        // Save new vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNumber(vehicleNumber);
        vehicle.setVehicleType(vehicleType);
        vehicle.setUser(user);

        return vehicleRepository.save(vehicle);
    }
}