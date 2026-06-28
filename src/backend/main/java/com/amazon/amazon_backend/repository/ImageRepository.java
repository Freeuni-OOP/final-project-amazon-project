package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByProduct_ProductId(Integer productId);

    void deleteByProduct_ProductId(Integer productId);
}
