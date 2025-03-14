package com.fmahadybd.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.entity.User;
import com.fmahadybd.backend.repository.ArtRepository;
import com.fmahadybd.backend.repository.UserRepository;
import com.fmahadybd.backend.response.ProfileResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ArtRepository artRepository;

    public ProfileResponse getProfileDetails(String username) {
        ProfileResponse profileResponse = new ProfileResponse();

        Optional<User> userOptional = userRepository.findByEmail(username);

     

        User user = userOptional.get();
        List<Art> arts = artRepository.findByUser(user);

        profileResponse.setName(user.getName());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setDate(user.getDob());
        profileResponse.setRole(user.getRole().toString());
        profileResponse.setArts(arts);
        

        return profileResponse;
    }

}
