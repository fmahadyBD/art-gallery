package com.fmahadybd.backend.service;

import java.util.List;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fmahadybd.backend.enmus.ROLE;
import com.fmahadybd.backend.entity.Token;
import com.fmahadybd.backend.entity.User;
import com.fmahadybd.backend.jwt.JwtService;
import com.fmahadybd.backend.repository.TokenRepository;
import com.fmahadybd.backend.repository.UserRepository;
import com.fmahadybd.backend.request.LoginRequest;
import com.fmahadybd.backend.request.NewUserRequest;
import com.fmahadybd.backend.response.AuthenticationResponse;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void emailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    }

    public void registerUser(NewUserRequest newUserRequest) {

        emailExists(newUserRequest.getEmail());

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
        sendActivevationEmail(user);

    }

    private void sendActivevationEmail(User user) {
        String activationLink = "http://localhost:8080/api/authenticate/activate/" + user.getId();
        String mailText = "<h2> Dear User</h2>" + "<p>Please click on the following link to activate your account</p>"
                + "<a href='" + activationLink + "'>Activate</a>";
        String subject = "Confirm your email";
        try {
            emailService.sendSimpleEmail(user.getEmail(), subject, mailText);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email");
        }

    }

    public String activeUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        userRepository.save(user);
        return "User activated successfully";
    }

    private void savedToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setLogout(false);
        tokenRepository.save(token);
    }

    private void removeAllTokenByUser(User user) {
        List<Token> validToken = tokenRepository.findAllTokenByUser(user.getId());

        if (validToken.isEmpty()) {
            return;
        }
        // Mark each token as logout
        validToken.forEach(t -> t.setLogout(true));

        tokenRepository.saveAll(validToken);

    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
       

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));
        } catch (AuthenticationException ex) {
            throw new RuntimeException("Invalid email or password");
        }

        // Retrieve the user
        User user = userRepository.findByEmail(loginRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate JWT
        String jwt = jwtService.generateToken(user);


        // Remove all previous tokens and save the new one
        removeAllTokenByUser(user);
        savedToken(jwt, user);

        return new AuthenticationResponse(jwt, "User logged in successfully");
    }

}
