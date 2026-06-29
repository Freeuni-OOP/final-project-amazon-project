package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderTest {
    @Test
    public void testConstructorAndGetters() {
        User buyer=new User();
        buyer.setId(100);
        BigDecimal totalAmount=new BigDecimal("250.50");
        LocalDateTime datetime=LocalDateTime.now();

        Order order=new Order(buyer, totalAmount, datetime);

        assertEquals(100, order.getBuyer().getId());
        assertEquals(totalAmount, order.getTotalAmount());
        assertEquals(datetime, order.getDatetime());
        assertNull(order.getOrderId());
    }

    @Test
    public void testSetters(){
        Order order=new Order();
        User buyer=new User();
        buyer.setId(205);
        order.setBuyer(buyer);

        BigDecimal totalAmount=new BigDecimal("1999.37");
        order.setTotalAmount(totalAmount);
        LocalDateTime datetime=LocalDateTime.now();
        order.setDatetime(datetime);
        order.setOrderId(3);

        assertEquals(205, order.getBuyer().getId());
        assertEquals(totalAmount, order.getTotalAmount());
        assertEquals(datetime, order.getDatetime());
        assertEquals(3, order.getOrderId());
    }
}
