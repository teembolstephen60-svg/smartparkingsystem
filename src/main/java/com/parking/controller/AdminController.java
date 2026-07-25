
package com.parking.controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/test")
    public String adminTest(Authentication authentication) {
        return "Welcome, ADMIN " + authentication.getName() + "! You have admin access.";
    }
}