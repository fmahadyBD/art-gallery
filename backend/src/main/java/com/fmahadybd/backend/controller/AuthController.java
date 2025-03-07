package com.fmahadybd.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmahadybd.backend.request.NewUserRequest;
import com.fmahadybd.backend.response.RegistrationResponse;
import com.fmahadybd.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;
    

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody NewUserRequest newUserRequest) {
        if (authService.emailExists(newUserRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new RegistrationResponse("Email already exists"));
        }
        authService.registerUser(newUserRequest);
        return ResponseEntity.ok(new RegistrationResponse("User registered successfully"));
    }
    
}
