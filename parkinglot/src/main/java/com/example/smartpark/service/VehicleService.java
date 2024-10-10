package com.example.smartpark.service;

import com.example.smartpark.model.ParkingLot;
import com.example.smartpark.model.Vehicle;
import com.example.smartpark.repository.VehicleRepository;
import com.example.smartpark.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Vehicle registerVehicle(Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByLicensePlate(vehicle.getLicensePlate());
        if (existingVehicle.isPresent()) {
            throw new IllegalArgumentException("A vehicle with this license plate already exists.");
        }

        if (vehicle.getLicensePlate() == null || vehicle.getOwnerName() == null || vehicle.getType() == null) {
            throw new IllegalArgumentException("License plate, owner name, and vehicle type are required");
        }

        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> getVehicle(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    public void updateVehicleParking(Vehicle vehicle, ParkingLot parkingLot) {
        if (vehicle.getParkingLot() != null && parkingLot != null) {
            throw new IllegalArgumentException("Vehicle is already parked in another lot");
        }

        vehicle.setParkingLot(parkingLot);

        vehicleRepository.save(vehicle);
    }

    public void checkOutVehicle(Vehicle vehicle) {
        vehicle.setParkingLot(null);
        vehicleRepository.save(vehicle);
    }
}
