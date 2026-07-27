
package com.parking.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Booking;
import com.parking.model.ParkingSpace;
import com.parking.model.user;
import com.parking.repository.BookingRepository;
import com.parking.repository.ParkingSpaceRepository;
import com.parking.repository.UserRepository;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired private UserRepository userRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private ParkingSpaceRepository parkingSpaceRepository;
    @GetMapping("/summary")
    public Map<String, Long> summary() {
        return Map.of(
            "userCount", userRepository.count(),
            "bookingCount", bookingRepository.count(),
            "spaceCount", parkingSpaceRepository.count()
        );
    }
    @GetMapping("/users")
    public List<user> allUsers() {
        return userRepository.findAll();
    }
    @GetMapping("/bookings")
    public List<Booking> allBookings() {
        return bookingRepository.findAll();
    }
    @GetMapping("/spaces")
    public List<ParkingSpace> allSpaces() {
        return parkingSpaceRepository.findAll();
    }
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        if (!bookingRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("deleted", id));
    }
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return userRepository.findById(id)
            .map(u -> {
                u.setRole(body.get("role"));
                userRepository.save(u);
                return ResponseEntity.ok(u);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}