package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.CategoryResponse;
import com.amazon.amazon_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/name/{name}")
    public CategoryResponse getByCategoryName(@PathVariable String name){
        return categoryService.getByCategoryName(name);
    }
}
