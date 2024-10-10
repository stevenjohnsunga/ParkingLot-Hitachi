package com.example.smartpark.controller;

import com.example.smartpark.model.ParkingLot;
import com.example.smartpark.model.Vehicle;
import com.example.smartpark.service.ParkingLotService;
import com.example.smartpark.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping("/register")
    public ResponseEntity<?> registerVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle registeredVehicle = vehicleService.registerVehicle(vehicle);
            return ResponseEntity.ok(registeredVehicle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/check-in/{lotId}")
    public ResponseEntity<String> checkInVehicle(@RequestParam String licensePlate, @PathVariable String lotId) {
        Optional<ParkingLot> parkingLotOpt = parkingLotService.getParkingLot(lotId);
        Optional<Vehicle> vehicleOpt = vehicleService.getVehicle(licensePlate);

        if (parkingLotOpt.isPresent() && vehicleOpt.isPresent()) {
            ParkingLot parkingLot = parkingLotOpt.get();
            Vehicle vehicle = vehicleOpt.get();

            if (parkingLotService.isFull(parkingLot)) {
                return ResponseEntity.badRequest().body("Parking lot is full");
            }

            if (vehicle.getParkingLot() != null) {
                return ResponseEntity.badRequest().body("Vehicle is already parked in another lot");
            }

            parkingLotService.updateOccupiedSpaces(parkingLot, 1);
            vehicleService.updateVehicleParking(vehicle, parkingLot);
            return ResponseEntity.ok("Vehicle checked in successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/check-out/{lotId}")
    public ResponseEntity<String> checkOutVehicle(@RequestParam String licensePlate, @PathVariable String lotId) {
        Optional<ParkingLot> parkingLotOpt = parkingLotService.getParkingLot(lotId);
        Optional<Vehicle> vehicleOpt = vehicleService.getVehicle(licensePlate);

        if (parkingLotOpt.isPresent() && vehicleOpt.isPresent()) {
            ParkingLot parkingLot = parkingLotOpt.get();
            Vehicle vehicle = vehicleOpt.get();

            if (vehicle.getParkingLot() == null || !vehicle.getParkingLot().getLotId().equals(lotId)) {
                return ResponseEntity.badRequest().body("Vehicle is not parked in this lot.");
            }

            parkingLotService.updateOccupiedSpaces(parkingLot, -1);
            vehicleService.checkOutVehicle(vehicle);
            return ResponseEntity.ok("Vehicle checked out successfully.");
        }
        return ResponseEntity.notFound().build();
    }

}
