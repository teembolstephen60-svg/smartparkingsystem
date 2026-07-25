
package com.parking.controller;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Booking;
import com.parking.model.Notification;
import com.parking.model.ParkingSpace;
import com.parking.model.user;
import com.parking.repository.BookingRepository;
import com.parking.repository.NotificationRepository;
import com.parking.repository.ParkingSpaceRepository;
import com.parking.repository.UserRepository;
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @PostMapping("/{spaceId}")
    public Booking createBooking(@PathVariable Long spaceId, Authentication authentication) {
        user currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ParkingSpace space = parkingSpaceRepository.findById(spaceId)
                .orElseThrow(() -> new RuntimeException("Parking space not found"));
        space.setStatus("OCCUPIED");
        parkingSpaceRepository.save(space);
        Booking booking = new Booking(currentUser, space, LocalDateTime.now(), "CONFIRMED");
        Booking savedBooking = bookingRepository.save(booking);
        String message = "Your booking for " + space.getZone() + " (Space #" + space.getSpaceId() + ") is confirmed.";
        Notification notification = new Notification(currentUser, message, LocalDateTime.now());
        notificationRepository.save(notification);
        return savedBooking;
    }
    @GetMapping("/my")
    public List<Booking> getMyBookings(Authentication authentication) {
        user currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepository.findAll()
                .stream()
                .filter(b -> b.getUser().getUsername().equals(currentUser.getUsername()))
                .toList();
    }
}