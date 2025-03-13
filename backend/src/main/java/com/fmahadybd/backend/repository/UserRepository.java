package com.fmahadybd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmahadybd.backend.entity.User;
import java.util.Optional;
import java.util.List;




@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);

    
}
