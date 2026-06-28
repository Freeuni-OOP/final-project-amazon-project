package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.TransactionResponse;
import com.amazon.amazon_backend.model.Transaction;
import com.amazon.amazon_backend.model.TransactionStatus;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.repository.TransactionRepository;
import com.amazon.amazon_backend.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository tranRepo;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction sampleTransaction;
    private User buyer;
    private User seller;
    private Order order;

    @BeforeEach
    public void setUp() {
        buyer = new User();
        buyer.setId(1);
        buyer.setUsername("buyerUser");

        seller = new User();
        seller.setId(2);
        seller.setUsername("sellerUser");

        order = new Order();
        order.setOrderId(100);
        order.setBuyer(buyer);

        sampleTransaction = new Transaction(order, buyer, seller, BigDecimal.valueOf(50.00), TransactionStatus.PENDING);
        sampleTransaction.setId(500);
        sampleTransaction.setItems(new ArrayList<>());
    }

    @Test
    public void testGetTransactionByIdWhenUserIsBuyer() {
        when(tranRepo.findById(500)).thenReturn(Optional.of(sampleTransaction));

        TransactionResponse response = transactionService.getTransactionById(1, 500);

        assertNotNull(response);
        assertEquals(500, response.getId());
        assertEquals("buyerUser", response.getBuyerName());
        verify(tranRepo, times(1)).findById(500);
    }

    @Test
    public void testGetTransactionByIdWhenUserIsNotAuthorized() {
        when(tranRepo.findById(500)).thenReturn(Optional.of(sampleTransaction));

        assertThrows(SecurityException.class, () -> {
            transactionService.getTransactionById(999, 500);
        });

        verify(tranRepo, times(1)).findById(500);
    }

    @Test
    public void testGetTransactionByIdWhenNotFound() {
        when(tranRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            transactionService.getTransactionById(1, 99);
        });

        verify(tranRepo, times(1)).findById(99);
    }

    @Test
    public void testUpdateTransactionStatusWhenSuccessful() {
        when(tranRepo.findById(500)).thenReturn(Optional.of(sampleTransaction));
        when(tranRepo.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponse response = transactionService.updateTransactionStatus(seller.getId(), 500, TransactionStatus.SUCCESS);

        assertNotNull(response);
        assertEquals(TransactionStatus.SUCCESS, response.getStatus());
        verify(tranRepo, times(1)).save(sampleTransaction);
    }

    @Test
    public void testUpdateTransactionStatusWhenUserIsNotSeller() {
        when(tranRepo.findById(500)).thenReturn(Optional.of(sampleTransaction));

        assertThrows(SecurityException.class, () -> {
            transactionService.updateTransactionStatus(1, 500, TransactionStatus.SUCCESS);
        });

        verify(tranRepo, never()).save(any());
    }

    @Test
    public void testUpdateTransactionStatusWhenMovingBackwardFromCompleted() {
        sampleTransaction.setStatus(TransactionStatus.SUCCESS);
        when(tranRepo.findById(500)).thenReturn(Optional.of(sampleTransaction));

        assertThrows(IllegalStateException.class, () -> {
            transactionService.updateTransactionStatus(2, 500, TransactionStatus.PENDING);
        });

        verify(tranRepo, never()).save(any());
    }

    @Test
    public void testGetUserTransactions() {
        List<Transaction> mockList = Arrays.asList(sampleTransaction);
        when(tranRepo.findByBuyerIdOrSellerId(1, 1)).thenReturn(mockList);

        List<TransactionResponse> result = transactionService.getUserTransactions(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tranRepo, times(1)).findByBuyerIdOrSellerId(1, 1);
    }

    @Test
    public void testGetUserPurchaseTransactions() {
        List<Transaction> mockList = Arrays.asList(sampleTransaction);
        when(tranRepo.findByBuyerId(1)).thenReturn(mockList);

        List<TransactionResponse> result = transactionService.getUserPurchaseTransactions(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tranRepo, times(1)).findByBuyerId(1);
    }

    @Test
    public void testGetUserSaleTransactions() {
        List<Transaction> mockList = Arrays.asList(sampleTransaction);
        when(tranRepo.findBySellerId(2)).thenReturn(mockList);

        List<TransactionResponse> result = transactionService.getUserSaleTransactions(2);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tranRepo, times(1)).findBySellerId(2);
    }

}
