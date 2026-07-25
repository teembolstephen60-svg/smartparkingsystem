
package com.parking.repository;
import com.parking.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
}