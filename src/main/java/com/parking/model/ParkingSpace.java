
package com.parking.model;
import jakarta.persistence.*;
@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;
    @Column(nullable = false)
    private String zone;
    @Column(nullable = false)
    private String status; // e.g. "AVAILABLE" or "OCCUPIED"
    public ParkingSpace() {}
    public ParkingSpace(String zone, String status) {
        this.zone = zone;
        this.status = status;
    }
    public Long getSpaceId() { return spaceId; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}