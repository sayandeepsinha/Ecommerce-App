package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthResponse;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 * Handles user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow Next.js frontend
public class AuthController {

    private final UserService userService;
    @Value("${app.allowAdminRegistration:false}")
    private boolean allowAdminRegistration;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Login an existing user
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Dev-only: Register a new ADMIN user when enabled by property app.allowAdminRegistration=true
     * POST /api/auth/register-admin
     */
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterRequest request) {
        if (!allowAdminRegistration) {
            return ResponseEntity.status(403).build();
        }
        AuthResponse response = userService.registerAdmin(request);
        return ResponseEntity.ok(response);
    }
}
