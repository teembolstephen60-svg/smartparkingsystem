
package com.parking.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Booking;
import com.parking.repository.BookingRepository;
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/bookings")
    public List<Map<String, Object>> getBookingReport() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(b -> {
            Map<String, Object> row = new HashMap<>();
            row.put("bookingId", b.getBookingId());
            row.put("username", b.getUser().getUsername());
            row.put("zone", b.getSpace().getZone());
            row.put("spaceId", b.getSpace().getSpaceId());
            row.put("status", b.getStatus());
            row.put("bookingTime", b.getBookingTime());
            return row;
        }).collect(Collectors.toList());
    }
    @GetMapping("/summary")
    public Map<String, Object> getSummaryReport() {
        List<Booking> bookings = bookingRepository.findAll();
        long confirmed = bookings.stream().filter(b -> "CONFIRMED".equalsIgnoreCase(b.getStatus())).count();
        long cancelled = bookings.stream().filter(b -> "CANCELLED".equalsIgnoreCase(b.getStatus())).count();
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalBookings", bookings.size());
        summary.put("confirmed", confirmed);
        summary.put("cancelled", cancelled);
        return summary;
    }
}