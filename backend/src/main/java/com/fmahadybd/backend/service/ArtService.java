package com.fmahadybd.backend.service;

import com.fmahadybd.backend.entity.Category;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.repository.ArtRepository;
import com.fmahadybd.backend.request.ArtRequest;

@Service
@RequiredArgsConstructor
public class ArtService {
    private final ArtRepository artRepository;

    public void saveArt(ArtRequest artRequest) {
        Art art = new Art();
        art.setName(artRequest.getName());
        artRepository.save(art);

    }

    public void updateArt(ArtRequest artRequest, Integer id) {
        Art art = artRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Art not found"));

        if (artRequest.getName() != null && !artRequest.getName().isEmpty()) {
            art.setName(artRequest.getName());
        }

        if (artRequest.getDescription() != null && !artRequest.getDescription().isEmpty()) {
            art.setDescription(artRequest.getDescription());
        }

        if (artRequest.getImage() != null && !artRequest.getImage().isEmpty()) {
            art.setImage(artRequest.getImage());
        }

        if (artRequest.getPrice() != null && !artRequest.getPrice().isEmpty()) {
            art.setPrice(artRequest.getPrice());
        }

        if (artRequest.getArtist() != null && !artRequest.getArtist().isEmpty()) {
            art.setArtist(artRequest.getArtist());
        }

        if (artRequest.getStatus() != null && !artRequest.getStatus().isEmpty()) {
            art.setStatus(artRequest.getStatus());
        }

        if (artRequest.getCategoryId() != null) {
            Category category = new Category(); // Ensure this is the correct Category class from your project
            category.setId(artRequest.getCategoryId());
            art.setCategory(category);
        }

        artRepository.save(art);
    }

    public void deleteArt(Integer id) {
        Art art = artRepository.findById(id).get();

        if (art == null) {
            throw new RuntimeException("Art not found");
        }
        artRepository.deleteById(id);
    }

    public Art getArt(Integer id) {
        return artRepository.findById(id).get();
    }

}
