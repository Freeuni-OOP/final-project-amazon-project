package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyer(User buyer);
    List<Order> findByBuyer_Id(Integer userId);
    List<Order> findByDatetime(LocalDateTime dateTime);
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od WHERE od.product.seller.id = :sellerId")
    List<Order> findBySellerId(@Param("sellerId") Integer sellerId);
}