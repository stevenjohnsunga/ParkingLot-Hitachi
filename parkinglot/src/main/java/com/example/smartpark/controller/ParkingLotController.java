package com.example.smartpark.controller;

import com.example.smartpark.model.ParkingLot;
import com.example.smartpark.model.Vehicle;
import com.example.smartpark.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/register")
    public ResponseEntity<ParkingLot> registerParkingLot(@RequestBody ParkingLot parkingLot) {
        return ResponseEntity.ok(parkingLotService.registerParkingLot(parkingLot));
    }

    @GetMapping("/{lotId}")
    public ResponseEntity<ParkingLot> getParkingLot(@PathVariable String lotId) {
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLot(lotId);
        return parkingLot.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{lotId}/occupancy")
    public ResponseEntity<String> getOccupancy(@PathVariable String lotId) {
        System.out.println("Received lotId: " + lotId);
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLot(lotId);
        if (parkingLot.isPresent()) {
            int occupied = parkingLot.get().getOccupiedSpaces();
            int capacity = parkingLot.get().getCapacity();
            return ResponseEntity.ok("Occupied: " + occupied + "/" + capacity);
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/{lotId}/vehicles")
    public ResponseEntity<List<Vehicle>> getVehiclesInLot(@PathVariable String lotId) {
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLot(lotId);
        if (parkingLot.isPresent()) {
            List<Vehicle> vehicles = parkingLot.get().getVehicles();
            return ResponseEntity.ok(vehicles);
        }
        return ResponseEntity.notFound().build();
    }

}