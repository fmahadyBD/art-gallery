package com.fmahadybd.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.entity.Category;
import com.fmahadybd.backend.response.ApiResponse;
import com.fmahadybd.backend.service.ArtService;
import com.fmahadybd.backend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    // private final AdminService adminservice;
    private final ArtService artService;
    private final CategoryService categoryService;

    @GetMapping("/get-all-art")
    public ResponseEntity<ApiResponse> getArts() {
        try {
            List<Art> arts = artService.getAllArtsWitthoutPagination();
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Arts retrieved successfully", arts));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to retrieve arts: " + e.getMessage(), null));
        }
    }

     @GetMapping("/get-categories")
     public ResponseEntity<ApiResponse> getAllCategory(){
        try{
            List<Category> getAllCategories = categoryService.getAllCategory();
            return ResponseEntity.ok().body(new ApiResponse(true,"Findall category",getAllCategories));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,"Fail to retrive categories",null));

        }
    }

}
