package com.fmahadybd.backend.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;
}
