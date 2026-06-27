package com.amazon.amazon_backend.utilityTest;

import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.utility.OrderConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderConverterTest {

    @Test
    void toOrderResponse_ShouldMapFieldsCorrectly() {
        User buyer = new User();
        buyer.setId(1);

        Order order = new Order();
        order.setOrderId(100);
        order.setBuyer(buyer);
        order.setTotalAmount(BigDecimal.valueOf(450.50));

        OrderResponse response = OrderConverter.toOrderResponse(order);

        Assertions.assertNotNull(response, "Response not null");
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals(BigDecimal.valueOf(450.50), response.getTotalAmount());
    }
}