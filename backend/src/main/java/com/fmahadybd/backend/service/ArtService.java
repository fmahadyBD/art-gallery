package com.fmahadybd.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.entity.Category;
import com.fmahadybd.backend.repository.ArtRepository;
import com.fmahadybd.backend.repository.CategoryRepository;
import com.fmahadybd.backend.request.ArtRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtService {
    private final ArtRepository artRepository;
    private final CategoryRepository categoryRepository;

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public List<Art> getAllArtsWitthoutPagination(){
        return artRepository.findAll();
    }
    public Page<Art> getAllArts(Pageable pageable) {
    return artRepository.findAll(pageable);
}


    public void saveArt(ArtRequest artRequest,MultipartFile image) throws IOException {
        if(image !=null && !image.isEmpty()){
            String imageFileName = saveImage(image, artRequest);
            artRequest.setImage(imageFileName);
            
        }
        Art art = new Art();
        art.setName(artRequest.getName());
        art.setDescription(artRequest.getDescription());
        art.setImage(artRequest.getImage());
        art.setPrice(artRequest.getPrice());
        art.setStatus(artRequest.getStatus());
        art.setArtist(artRequest.getArtist());

        Category category = categoryRepository.findById(artRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        art.setCategory(category);
        artRepository.save(art);

    }

    public void updateArt(ArtRequest artRequest, Integer id,MultipartFile image) throws IOException {

        if(image !=null && !image.isEmpty()){
            String imageFileName = saveImage(image, artRequest);
            artRequest.setImage(imageFileName);
            
        }
        
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
            Category category = new Category();
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

        if (art.getImage() != null && !art.getImage().isEmpty()) {
            deleteImage(art.getImage());
        }

        artRepository.deleteById(id);
    }

    public Art getArt(Integer id) {
        return artRepository.findById(id).get();
    }


    private String saveImage(MultipartFile image, ArtRequest art) throws IOException{
        
        Path uploadPath = Paths.get(imageUploadDir,"arts");
        if(!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String fileName = art.getName()+"_"+UUID.randomUUID();
        Path  filePath =uploadPath.resolve(fileName);

        Files.copy(image.getInputStream(),filePath);
        return fileName;
        
    }

    private void deleteImage(String fileName){
        try{
            Path imagePath = Paths.get(imageUploadDir,"arts",fileName);
            Files.deleteIfExists(imagePath);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
