package com.fmahadybd.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fmahadybd.backend.enmus.ROLE;
import com.fmahadybd.backend.entity.User;
import com.fmahadybd.backend.repository.UserRepository;
import com.fmahadybd.backend.request.NewUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void registerUser(NewUserRequest newUserRequest) {

        User user = new User();
        user.setEmail(newUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        user.setRole(ROLE.USER);
        user.setGender(newUserRequest.getGender());
        user.setDob(newUserRequest.getDob());

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(false);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        userRepository.save(user);

    }

}
