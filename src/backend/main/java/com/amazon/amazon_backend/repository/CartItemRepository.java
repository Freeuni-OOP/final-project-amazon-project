package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Integer>{
    List<CartItem>  findByUser_Id(Integer userId);
    List<CartItem> findByProduct_ProductId(Integer productId);
    Optional<CartItem> findByUser_IdAndProduct_ProductId(Integer userId, Integer productId);
    long countByUser_Id(Integer userId);
}
