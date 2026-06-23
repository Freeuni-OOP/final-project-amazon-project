package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.model.*;
import com.amazon.amazon_backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.amazon.amazon_backend.utility.TransactionConverter.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository tranRepo;

    public List<TransactionResponse> getUserTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findByBuyerIdOrSellerId(userId, userId));
    }

    public List<TransactionResponse> getUserPurchaseTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findByBuyerId(userId));
    }

    public List<TransactionResponse> getUserSaleTransactions(Integer userId){
        return tranListToTranRespList(tranRepo.findBySellerId(userId));
    }

    public TransactionResponse getTransactionById(Integer userId, Integer transactionId){
        Transaction transaction = tranRepo.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));

        boolean isBuyer = transaction.getBuyer().getId().equals(userId);
        boolean isSeller = transaction.getSeller().getId().equals(userId);
        String msg = "Access Denied: You do not have permission to view this transaction.";
        permissionCheck(!isBuyer && !isSeller, msg);

        return toDetailedTranResp(transaction);
    }

    @Transactional
    public void createTransactionsForOrder(Order order, List<OrderDetails> orderItems){
        Map<User, List<OrderDetails>> itemsBySeller = orderItems.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller()));

        for (Map.Entry<User, List<OrderDetails>> entry : itemsBySeller.entrySet()) {
            User seller = entry.getKey();
            List<OrderDetails> sellerItems = entry.getValue();

            BigDecimal totalForSeller = sellerItems.stream()
                    .map(item -> item.getAmount().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Transaction transaction = new Transaction(
                    order,
                    order.getBuyer(),
                    seller,
                    totalForSeller,
                    TransactionStatus.PENDING
            );

            for (OrderDetails item : sellerItems) {
                transaction.addItem(item);
            }

            tranRepo.save(transaction);
        }
    }

    public TransactionResponse updateTransactionStatus(Integer userId, Integer transactionId, TransactionStatus newStatus){
        Transaction transaction = tranRepo.findById(transactionId)
                .orElseThrow(() -> new NoSuchElementException("Transaction not found with ID: " + transactionId));

        boolean isSeller = transaction.getSeller().getId().equals(userId);
        String msg = "Access Denied: Only the seller assigned to this transaction can modify its status.";
        permissionCheck(!isSeller, msg);

        statusChecks(transaction);

        transaction.setStatus(newStatus);
        Transaction updatedTransaction = tranRepo.save(transaction);
        return toDetailedTranResp(updatedTransaction);
    }

    /**
        Throws an exception if status goes backward.
        Written in case for an intrusion.
     */
    private void statusChecks(Transaction transaction){
        if (transaction.getStatus() == TransactionStatus.SUCCESS) {
            throw new IllegalStateException("Invalid Operation: A completed transaction cannot be modified.");
        }

        if (transaction.getStatus() == TransactionStatus.FAILED) {
            throw new IllegalStateException("Invalid Operation: A failed transaction cannot be modified.");
        }
    }

    private void permissionCheck(boolean shouldThrow, String message){
        if(shouldThrow) throw new SecurityException(message);
    }

}
