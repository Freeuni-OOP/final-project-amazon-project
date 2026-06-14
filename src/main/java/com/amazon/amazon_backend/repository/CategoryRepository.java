package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Integer id);
    Optional<Category> findCategoryByName(String name);
}