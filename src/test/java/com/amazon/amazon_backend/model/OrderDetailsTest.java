package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailsTest {

    @Test
    public void testConstructorAndGetters(){
        Product product=new Product();
        product.setProductId(84);

        Order order=new Order();
        order.setOrderId(9);

        Integer quantity=14;
        BigDecimal amount=new BigDecimal("1978.35");

        Transaction transaction=new Transaction();
        transaction.setId(3);

        OrderDetails orderDetail=new OrderDetails(null, product, order, quantity, amount);

        assertNull(orderDetail.getOrderDetailID());
        assertEquals(84, orderDetail.getProduct().getProductId());
        assertEquals(9, orderDetail.getOrder().getOrderId());
        assertEquals(14, orderDetail.getQuantity());
        assertEquals(amount, orderDetail.getAmount());
        assertEquals(3, orderDetail.getTransaction().getId());
    }

    @Test
    public void testSetters(){
        OrderDetails orderDetail=new OrderDetails();
        orderDetail.setOrderDetailID(67);

        Product product=new Product();
        product.setProductId(1967);

        Order order=new Order();
        order.setOrderId(78630);

        orderDetail.setProduct(product);
        orderDetail.setOrder(order);

        Transaction transaction=new Transaction();
        transaction.setId(3);

        Integer quantity=15;
        BigDecimal amount=new BigDecimal("625.95");
        orderDetail.setQuantity(quantity);
        orderDetail.setAmount(amount);
        orderDetail.setTransaction(transaction);

        assertEquals(67, orderDetail.getOrderDetailID());
        assertEquals(1967, orderDetail.getProduct().getProductId());
        assertEquals(78630, orderDetail.getOrder().getOrderId());
        assertEquals(15, orderDetail.getQuantity());
        assertEquals(amount, orderDetail.getAmount());
        assertEquals(3, orderDetail.getTransaction().getId());
    }
}