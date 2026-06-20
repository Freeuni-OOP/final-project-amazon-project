package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionConverter {
    public static TransactionResponse toTranResp(Transaction transaction){
        return TransactionResponse.builder()
                .id(transaction.getId())
                .orderId(transaction.getOrder().getOrderId())
                .buyerId(transaction.getBuyer().getId())
                .buyerName(transaction.getBuyer().getUsername())
                .sellerId(transaction.getSeller().getId())
                .sellerName(transaction.getSeller().getUsername())
                .totalAmount(transaction.getTotalAmount())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    public static List<TransactionResponse> tranListToTranRespList(List<Transaction> transactions){
        return transactions.stream()
                .map(TransactionConverter::toTranResp)
                .collect(Collectors.toList());
    }
}
