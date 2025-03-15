package com.fmahadybd.backend.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fmahadybd.backend.service.ForgetPasswordService;

@RestController
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/verification")
    public ResponseEntity<Map<String, String>> getVerificationCode(@RequestPart String email) {
        Map<String, String> response = new HashMap<>();
        try {
            forgetPasswordService.getVarification(email);
            response.put("Message", "Verification code successfully sent");
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            response.put("Error", "User not found by this email");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        catch (Exception e) {
            response.put("Error", "Email probelem");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(@RequestPart String email,
            @RequestPart String password,
            @RequestPart long code) {
        Map<String, String> response = new HashMap<>();
        try {
            forgetPasswordService.chnagedPassword(code, email, password);
            response.put("Message", "Chnaged successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Error", "There have error to chnaged the password");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    
}
