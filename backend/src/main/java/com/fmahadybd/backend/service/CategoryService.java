package com.fmahadybd.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fmahadybd.backend.entity.Category;
import com.fmahadybd.backend.repository.CategoryRepository;
import com.fmahadybd.backend.request.CategoryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

    }

    public void updateCategory(CategoryRequest categoryRequest, Integer id) {

        Category category = categoryRepository.findById(id).get();

        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);

    }

    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).get();

        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
