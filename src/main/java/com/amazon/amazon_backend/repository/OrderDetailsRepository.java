package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.OrderDetails;
import com.amazon.amazon_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrder(Order order);
    List<OrderDetails> findByProduct(Product product);
}
