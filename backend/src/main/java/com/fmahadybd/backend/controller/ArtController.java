package com.fmahadybd.backend.controller;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.request.ArtRequest;
import com.fmahadybd.backend.response.ApiResponse;
import com.fmahadybd.backend.service.ArtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/arts")
@RequiredArgsConstructor
public class ArtController {

    private final ArtService artService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createArt(
            @Valid @RequestPart(value = "art") ArtRequest artRequest,
            @RequestParam(value = "image", required = true) MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Validation failed", errors));
        }

        try {
            artService.saveArt(artRequest, image);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Art created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to create art: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateArt(
            @Valid @RequestPart(value = "art") ArtRequest artRequest,
            @RequestParam(value = "image", required = true) MultipartFile image,
            BindingResult bindingResult,
            @PathVariable Integer id) {

                
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Validation failed", errors));
        }

        try {
            artService.updateArt(artRequest, id, image);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Art updated successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to update art: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteArt(@PathVariable Integer id) {
        System.out.println("IN the delete method of ArtController");
        try {
            artService.deleteArt(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Art deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to delete art: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getArt(@PathVariable Integer id) {
        try {
            Art art = artService.getArt(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Art retrieved successfully", art));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve art: " + e.getMessage(), null));
        }
    }
}
