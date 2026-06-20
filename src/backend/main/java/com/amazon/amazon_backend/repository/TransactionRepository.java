package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionByBuyerIdOrSellerId(Integer buyerId, Integer sellerId);

    List<Transaction> findTransactionByBuyerId(Integer buyerId);

    List<Transaction> findTransactionBySellerId(Integer sellerId);

}
