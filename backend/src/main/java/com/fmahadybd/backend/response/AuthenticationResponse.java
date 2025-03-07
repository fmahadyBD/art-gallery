package com.fmahadybd.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String token;
    private String message;
}
