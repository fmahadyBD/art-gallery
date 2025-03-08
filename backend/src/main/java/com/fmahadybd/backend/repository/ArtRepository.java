package com.fmahadybd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmahadybd.backend.entity.Art;

@Repository
public interface ArtRepository extends JpaRepository<Art, Integer> {
    
}
