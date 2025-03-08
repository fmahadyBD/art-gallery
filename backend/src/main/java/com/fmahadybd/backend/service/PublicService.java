package com.fmahadybd.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.repository.ArtRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicService {

    private final ArtRepository artRepository;

    public List<Art> getArts() {
        return artRepository.findAll();
    }

}
