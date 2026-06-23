package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.amazon.amazon_backend.utility.CategoryConverter.toCategoryResponse;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse getByCategoryName(String name){
        return toCategoryResponse(categoryRepository.findByCategoryName(name).orElseThrow(() -> new NoSuchElementException("Category not found")));
    }
}
