package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductRepository extends JpaRepository<Product, Integer>{

  List<Product> findByProductNameContainingIgnoreCase(String productName);
  List<Product> findByCategory_CategoryId(Integer categoryId);
  List<Product> findByCategory_CategoryNameIgnoreCase(String categoryName);
  List<Product> findBySeller_Id(Integer userId);

}
