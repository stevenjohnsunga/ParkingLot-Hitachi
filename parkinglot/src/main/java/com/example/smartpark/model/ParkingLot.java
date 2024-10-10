package com.example.smartpark.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class ParkingLot {

    @Id
    private String lotId;

    private String location;
    private int capacity;
    private int occupiedSpaces;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;

    public ParkingLot() {
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(int occupiedSpaces) {
        this.occupiedSpaces = occupiedSpaces;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @PrePersist
    private void prePersist() {
        if (this.lotId == null) {
            this.lotId = UUID.randomUUID().toString();  // Automatically generate UUID for lotId
        }
    }
}
