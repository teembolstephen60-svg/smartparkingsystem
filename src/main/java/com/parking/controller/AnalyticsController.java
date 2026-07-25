
package com.parking.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.repository.ParkingSpaceRepository;
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @GetMapping("/occupancy")
    public Map<String, Object> getOccupancyStats() {
        long total = parkingSpaceRepository.count();
        long occupied = parkingSpaceRepository.findAll()
                .stream()
                .filter(s -> "OCCUPIED".equalsIgnoreCase(s.getStatus()))
                .count();
        long available = total - occupied;
        double occupancyRate = total == 0 ? 0 : (occupied * 100.0 / total);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalSpaces", total);
        stats.put("occupied", occupied);
        stats.put("available", available);
        stats.put("occupancyRatePercent", Math.round(occupancyRate * 100.0) / 100.0);
        return stats;
    }
}