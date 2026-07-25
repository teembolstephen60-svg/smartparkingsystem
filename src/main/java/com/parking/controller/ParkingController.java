
package com.parking.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.ParkingSpace;
import com.parking.repository.ParkingSpaceRepository;
@RestController
@RequestMapping("/api/parking")
public class ParkingController {
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @GetMapping("/available")
    public List<ParkingSpace> getAvailableSpaces() {
        return parkingSpaceRepository.findAll()
                .stream()
                .filter(space -> "AVAILABLE".equalsIgnoreCase(space.getStatus()))
                .collect(Collectors.toList());
    }
    @GetMapping("/all")
    public List<ParkingSpace> getAllSpaces() {
        return parkingSpaceRepository.findAll();
    }
}
