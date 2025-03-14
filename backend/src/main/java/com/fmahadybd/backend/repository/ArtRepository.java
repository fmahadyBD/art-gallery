package com.fmahadybd.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmahadybd.backend.entity.Art;
import com.fmahadybd.backend.entity.User;

@Repository
public interface ArtRepository extends JpaRepository<Art, Integer> {
    List<Art> findByUser(User user);
}
