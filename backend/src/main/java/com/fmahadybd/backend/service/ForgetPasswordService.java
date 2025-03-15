package com.fmahadybd.backend.service;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fmahadybd.backend.entity.User;
import com.fmahadybd.backend.entity.VerificationCode;
import com.fmahadybd.backend.repository.UserRepository;
import com.fmahadybd.backend.repository.VerificationCodeRepository;

import jakarta.mail.MessagingException;

@Service
public class ForgetPasswordService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // find by email for generation Vefication
    public void getVarification(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by this email: " + email));

        if (user != null) {
            Random random = new Random();
            long randomNumber = 10000000L + random.nextInt(90000000);
            VerificationCode newCode = new VerificationCode();
            newCode.setActive(true);
            newCode.setCode(randomNumber);
            newCode.setEmail(email);

            verificationCodeRepository.save(newCode);
            sendActivationEmail(email, randomNumber);
        }

    }

    private void sendActivationEmail(String email,long code){
        String emailText ="<h2> Your Varification code:  </h2>"
        +"<P> Code is: </p>"+
        "h2>"+code+"</h2>";
        String subject ="Verification code";
        try{
            emailService.sendSimpleEmail(email, subject, emailText);

        }catch(MessagingException e){
            throw new RuntimeException("Can not sent email properly");
        }

    }


    // confirmed the changed

    public void chnagedPassword(long code, String email, String password){
        VerificationCode verificationCode= verificationCodeRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Not found any valied code by this email"));

        if(verificationCode != null && verificationCode.isActive()){
            verificationCode.setActive(false);
            verificationCodeRepository.save(verificationCode);

            User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Not found any user by this email"));
            user.setPassword(password);
            userRepository.save(user);
        }
    }

}