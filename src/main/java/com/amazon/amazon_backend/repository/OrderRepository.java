package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
    List<Order> findByDateTime(LocalDateTime dateTime);
}
