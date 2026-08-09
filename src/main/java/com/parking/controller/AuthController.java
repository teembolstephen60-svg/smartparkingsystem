

package com.parking.controller;
import com.parking.model.user;
import com.parking.repository.UserRepository;
import com.parking.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        System.out.println(">>> REGISTER ENDPOINT HIT <<<");
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }
        Optional<user> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        String hashedPassword = passwordEncoder.encode(password);
        user newUser = new user(username, hashedPassword, "USER");
        userRepository.save(newUser);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Optional<user> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        user existingUser = userOpt.get();
        if (!passwordEncoder.matches(password, existingUser.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        existingUser.setLastLogin(LocalDateTime.now());
        userRepository.save(existingUser);
        String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", existingUser.getUsername());
        response.put("role", existingUser.getRole());
        return ResponseEntity.ok(response);
    }
}

