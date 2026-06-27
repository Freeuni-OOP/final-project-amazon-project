package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByBuyerIdOrSellerId(Integer buyerId, Integer sellerId);

    List<Transaction> findByBuyerId(Integer buyerId);

    List<Transaction> findBySellerId(Integer sellerId);

    Optional<Transaction> findById(Integer id);

}
