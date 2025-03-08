package com.fmahadybd.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmahadybd.backend.entity.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}