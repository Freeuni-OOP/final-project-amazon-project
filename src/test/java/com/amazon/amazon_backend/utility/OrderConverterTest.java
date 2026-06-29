package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.OrderDetailsResponse;
import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderConverterTest {

    @Test
    void toOrderResponse_ShouldMapFieldsCorrectly_WithoutDetails() {
        User buyer = new User();
        buyer.setId(1);
        buyer.setUsername("testUser");

        Order order = new Order();
        order.setOrderId(100);
        order.setBuyer(buyer);
        order.setTotalAmount(BigDecimal.valueOf(450.50));
        order.setDatetime(LocalDateTime.now());

        OrderResponse response = OrderConverter.toOrderResponse(order);

        Assertions.assertNotNull(response, "Response should not be null");
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("testUser", response.getUsername());
        Assertions.assertEquals(BigDecimal.valueOf(450.50), response.getTotalAmount());
        Assertions.assertNotNull(response.getDateTime());
        Assertions.assertNull(response.getItems(), "Items should be null if OrderDetails is null");
    }

    @Test
    void toOrderResponse_ShouldMapFieldsCorrectly_WithDetails() {
        User buyer = new User();
        buyer.setId(1);

        Product product = new Product();
        product.setProductId(10);
        product.setPrice(BigDecimal.valueOf(100));

        OrderDetails details = new OrderDetails();
        details.setProduct(product);
        details.setQuantity(2);

        Order order = new Order();
        order.setBuyer(buyer);
        order.setTotalAmount(BigDecimal.valueOf(200));
        order.setOrderDetails(List.of(details));

        OrderResponse response = OrderConverter.toOrderResponse(order);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getItems());
        Assertions.assertEquals(1, response.getItems().size());
        Assertions.assertEquals(10, response.getItems().get(0).getProductId());
    }

    @Test
    void toOrderDetailsResponse_WithTransaction_ShouldMapStatusCorrectly() {
        Product product = new Product();
        product.setProductId(5);
        product.setProductName("Wireless Mouse");
        product.setPrice(BigDecimal.valueOf(25.50));

        Transaction transaction = new Transaction();
        transaction.setStatus(TransactionStatus.SUCCESS);

        OrderDetails details = new OrderDetails();
        details.setProduct(product);
        details.setQuantity(2);
        details.setAmount(BigDecimal.valueOf(51.00));
        details.setTransaction(transaction);

        OrderDetailsResponse response = OrderConverter.toOrderDetailsResponse(details);

        Assertions.assertEquals(5, response.getProductId());
        Assertions.assertEquals("Wireless Mouse", response.getProductName());
        Assertions.assertEquals(BigDecimal.valueOf(25.50), response.getPrice());
        Assertions.assertEquals(2, response.getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(51.00), response.getAmount());
        Assertions.assertEquals(TransactionStatus.SUCCESS, response.getStatus());
    }

    @Test
    void toOrderDetailsResponse_WithoutTransaction_ShouldDefaultToPending() {
        Product product = new Product();
        product.setProductId(8);
        product.setProductName("Keyboard");
        product.setPrice(BigDecimal.valueOf(60.00));

        OrderDetails details = new OrderDetails();
        details.setProduct(product);
        details.setQuantity(1);
        details.setAmount(BigDecimal.valueOf(60.00));

        OrderDetailsResponse response = OrderConverter.toOrderDetailsResponse(details);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(TransactionStatus.PENDING, response.getStatus(),
                "Status should default to PENDING when transaction is null");
    }
}