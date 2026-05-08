package com.college.Parking.controller;

import com.college.Parking.model.ParkingSlot;
import com.college.Parking.model.ParkingZone;
import com.college.Parking.repository.ParkingSlotRepository;
import com.college.Parking.repository.ParkingZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/slot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotRepository slotRepository;

    @Autowired
    private ParkingZoneRepository zoneRepository;

    // Add new slot
    @PostMapping("/add")
    public ParkingSlot addSlot(
            @RequestParam String slotNumber,
            @RequestParam Long zoneId) {

        ParkingZone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        ParkingSlot slot = new ParkingSlot();
        slot.setSlotNumber(slotNumber);
        slot.setStatus("AVAILABLE");
        slot.setZone(zone);

        return slotRepository.save(slot);
    }
    @GetMapping("/all")
    public List<ParkingSlot> getAllSlots() {
        return slotRepository.findAll();
    }
    @GetMapping("/available")
    public List<ParkingSlot> getAvailableSlots() {
        return slotRepository.findByStatus("AVAILABLE");
    }
    @GetMapping("/occupied")
    public List<ParkingSlot> getOccupiedSlots() {
        return slotRepository.findByStatus("OCCUPIED");
    }
    @DeleteMapping("/deleteByNumber/{slotNumber}")
    public String deleteSlotByNumber(@PathVariable String slotNumber) {

        ParkingSlot slot = slotRepository.findBySlotNumber(slotNumber)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if ("OCCUPIED".equalsIgnoreCase(slot.getStatus())) {
            throw new RuntimeException("Cannot delete occupied slot");
        }

        slotRepository.delete(slot);

        return "Slot " + slotNumber + " deleted successfully";
    }
    @DeleteMapping("/delete/{slotId}")
    public String deleteSlot(@PathVariable Long slotId) {
        ParkingSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if ("OCCUPIED".equalsIgnoreCase(slot.getStatus())) {
            throw new RuntimeException("Cannot delete occupied slot");
        }

        slotRepository.delete(slot);
        return "Slot deleted successfully";
    }
    }

