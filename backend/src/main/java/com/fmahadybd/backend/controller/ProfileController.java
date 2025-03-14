package com.fmahadybd.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmahadybd.backend.response.ApiResponse;
import com.fmahadybd.backend.response.ProfileResponse;
import com.fmahadybd.backend.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;


    @GetMapping("/get/{username}")
    public ResponseEntity<ApiResponse> getProfileByUsername(@PathVariable String username) {
        try {
            ProfileResponse profileResponse = profileService.getProfileDetails(username);
            return ResponseEntity.ok(new ApiResponse(true, "Successfully retrieved profile", profileResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Some error happened!", null));
        }
    }
    
}
