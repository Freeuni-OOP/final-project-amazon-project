package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.model.TransactionStatus;
import com.amazon.amazon_backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponse>> getUserHistory(@PathVariable Integer userId) {
        List<TransactionResponse> history = transactionService.getUserTransactions(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/purchases")
    public ResponseEntity<List<TransactionResponse>> getPurchases(@PathVariable Integer userId) {
        List<TransactionResponse> purchases = transactionService.getUserPurchaseTransactions(userId);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<TransactionResponse>> getSales(@PathVariable Integer userId) {
        List<TransactionResponse> sales = transactionService.getUserSaleTransactions(userId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable Integer userId,
            @PathVariable Integer id) {

        TransactionResponse transaction = transactionService.getTransactionById(userId, id);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TransactionResponse> updateStatus(
            @PathVariable Integer userId,
            @PathVariable Integer id,
            @RequestParam TransactionStatus newStatus) {

        TransactionResponse updated = transactionService.updateTransactionStatus(userId, id, newStatus);
        return ResponseEntity.ok(updated);
    }
}