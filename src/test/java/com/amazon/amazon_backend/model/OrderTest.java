package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OrderTest {
    @Test
    public void testConstructorAndGetters() {
        Integer buyerId=100;
        double totalAmount=250.50;
        LocalDateTime datetime=LocalDateTime.now();

        Order order=new Order(buyerId, totalAmount, datetime);

        assertEquals(buyerId, order.getBuyerId());
        assertEquals(totalAmount, order.getTotalAmount());
        assertEquals(datetime, order.getDatetime());
        assertNull(order.getOrderId());
    }

    @Test
    public void testSetters(){
        Order order=new Order();

        order.setBuyerId(205);
        order.setTotalAmount(1999.37);
        LocalDateTime datetime=LocalDateTime.now();
        order.setDatetime(datetime);
        order.setOrderId(3);

        assertEquals(205, order.getBuyerId());
        assertEquals(1999.37, order.getTotalAmount());
        assertEquals(datetime, order.getDatetime());
        assertEquals(3, order.getOrderId());
    }
}
