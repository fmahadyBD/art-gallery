package com.fmahadybd.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
       
        authService.registerUser(newUserRequest);
        return ResponseEntity.ok(new RegistrationResponse("User registered successfully"));
    }
    

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activate(@PathVariable("id") int id) {
        try{
            String response = authService.activeUser(id);
            return ResponseEntity.ok(response);
        }catch(UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
