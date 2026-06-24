package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class TransactionTest {

    @Test
    public void testRelationship() {
        Transaction transaction = new Transaction();
        OrderDetails item = new OrderDetails();

        transaction.addItem(item);

        assertEquals(1, transaction.getItems().size());
        assertEquals(transaction, item.getTransaction());
    }

    @Test
    public void testGettersAndSetters() {
        Transaction transaction = new Transaction();
        BigDecimal amount = new BigDecimal("150.50");

        transaction.setTotalAmount(amount);
        transaction.setStatus(TransactionStatus.PENDING);

        assertEquals(amount, transaction.getTotalAmount());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    public void testOnCreate() {
        Transaction transaction = new Transaction();
        assertThat(transaction.getCreatedAt()).isNull();

        transaction.onCreate();

        assertThat(transaction.getCreatedAt()).isNotNull();

        assertThat(transaction.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

}