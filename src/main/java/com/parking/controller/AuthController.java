
package com.parking.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.parking.model.user;
import com.parking.repository.UserRepository;
@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new user());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                                @RequestParam String password) {
        user newUser = new user(username, passwordEncoder.encode(password), "USER");
        userRepository.save(newUser);
        return "redirect:/login?registered";
    }
}
