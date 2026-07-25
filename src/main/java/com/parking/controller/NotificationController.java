
package com.parking.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.model.Notification;
import com.parking.model.user;
import com.parking.repository.NotificationRepository;
import com.parking.repository.UserRepository;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/my")
    public List<Notification> getMyNotifications(Authentication authentication) {
        user currentUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findAll()
                .stream()
                .filter(n -> n.getUser().getUsername().equals(currentUser.getUsername()))
                .toList();
    }
}
