package com.example.smartpark.service;

import com.example.smartpark.model.ParkingLot;
import com.example.smartpark.repository.ParkingLotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public ParkingLot registerParkingLot(ParkingLot lot) {
        ParkingLot savedLot = parkingLotRepository.save(lot);
        System.out.println("Saved Parking Lot ID: " + savedLot.getLotId());
        return savedLot;
    }

    @Transactional
    public Optional<ParkingLot> getParkingLot(String lotId) {
        return parkingLotRepository.findById(lotId);
    }

    public boolean isFull(ParkingLot parkingLot) {
        return parkingLot.getOccupiedSpaces() >= parkingLot.getCapacity();
    }

    public void updateOccupiedSpaces(ParkingLot parkingLot, int delta) {
        int updatedOccupiedSpaces = parkingLot.getOccupiedSpaces() + delta;
        if (updatedOccupiedSpaces < 0 || updatedOccupiedSpaces > parkingLot.getCapacity()) {
            throw new IllegalArgumentException("Invalid number of occupied spaces");
        }

        parkingLot.setOccupiedSpaces(updatedOccupiedSpaces);
        parkingLotRepository.save(parkingLot);
    }
}
