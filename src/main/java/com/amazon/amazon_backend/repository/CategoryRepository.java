package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


// <Category, Long> this tell Spring that we have table named: Category
// and its id is Long type variable
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // because it extends JpaRepository<Category, Long> it automatically has methods:
    // save(Category entity)
    // findAll() — Fetches all categories as a List<Category>
    // deleteById(ID id)

    // optional means that it might be null. we use it to avoid null pointer exceptions
    Optional<Category> findCategoryById(Long id);
}