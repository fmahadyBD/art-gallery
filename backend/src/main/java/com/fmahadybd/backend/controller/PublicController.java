package com.fmahadybd.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmahadybd.backend.response.ApiResponse;
import com.fmahadybd.backend.service.PublicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;

    @GetMapping("/arts")
    public ResponseEntity<ApiResponse> getArts() {
        return ResponseEntity.ok(new ApiResponse(true, "Arts fetched successfully", publicService.getArts()));
    }

}
