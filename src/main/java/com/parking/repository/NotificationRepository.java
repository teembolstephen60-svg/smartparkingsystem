package com.parking.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.model.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
 

