package com.college.Parking.controller;

import com.college.Parking.model.ParkingZone;
import com.college.Parking.repository.ParkingSlotRepository;
import com.college.Parking.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/zone")
public class ParkingZoneController {

    @Autowired
    private ParkingZoneRepository zoneRepository;
    @Autowired
    private ParkingSlotRepository slotRepository;

    // 🔥 ADD ZONE
    @PostMapping("/add")
    public ParkingZone addZone(@RequestParam String zoneName,
                               @RequestParam String vehicleType) {

        Optional<ParkingZone> existingZone =
                zoneRepository.findByZoneNameAndVehicleType(zoneName, vehicleType);

        if (existingZone.isPresent()) {
            throw new RuntimeException("Zone already exists");
        }

        ParkingZone zone = new ParkingZone();
        zone.setZoneName(zoneName);
        zone.setVehicleType(vehicleType);
        zone.setTotalSlots(0);

        return zoneRepository.save(zone);
    }

    // 🔥 GET ALL ZONES
    @GetMapping("/all")
    public List<ParkingZone> getAllZones() {
        List<ParkingZone> zones = zoneRepository.findAll();

        for (ParkingZone zone : zones) {
            long liveCount = slotRepository.countByZone_Id(zone.getId());
            zone.setTotalSlots((int) liveCount);
        }

        return zones;
    }
}