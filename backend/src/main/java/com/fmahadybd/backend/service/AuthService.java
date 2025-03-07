package com.fmahadybd.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fmahadybd.backend.enmus.ROLE;
import com.fmahadybd.backend.entity.User;
import com.fmahadybd.backend.repository.UserRepository;
import com.fmahadybd.backend.request.NewUserRequest;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

    public String activeUser(Integer id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        return "User activated successfully";
    }

}
