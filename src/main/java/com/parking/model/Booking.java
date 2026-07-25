
package com.parking.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;
    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private ParkingSpace space;
    @Column(nullable = false)
    private LocalDateTime bookingTime;
    @Column(nullable = false)
    private String status; // e.g. "CONFIRMED", "CANCELLED", "COMPLETED"
    public Booking() {}
    public Booking(user user, ParkingSpace space, LocalDateTime bookingTime, String status) {
        this.user = user;
        this.space = space;
        this.bookingTime = bookingTime;
        this.status = status;
    }
    public Long getBookingId() { return bookingId; }
    public user getUser() { return user; }
    public void setUser(user user) { this.user = user; }
    public ParkingSpace getSpace() { return space; }
    public void setSpace(ParkingSpace space) { this.space = space; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
