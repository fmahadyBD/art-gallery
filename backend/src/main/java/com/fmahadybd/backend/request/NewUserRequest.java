package com.fmahadybd.backend.request;

import java.util.Date;

import com.fmahadybd.backend.enmus.GENDER;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {

    @NotBlank(message = "Email is required")
    @Column(unique = true)
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password is invalid")
    private String password;


    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth is invalid")
    private Date dob;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Gender is required")
    private GENDER gender;

}
