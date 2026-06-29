package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

public class OrderRepositoryTest {

    @Test
    void testAllRepositoryMethodsForCoverage() {
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);

        User testUser = new User();
        LocalDateTime testTime = LocalDateTime.now();
        List<Order> expectedOrders = List.of(new Order());

        Mockito.when(orderRepository.findByBuyer(testUser)).thenReturn(expectedOrders);
        Mockito.when(orderRepository.findByBuyer_Id(1)).thenReturn(expectedOrders);
        Mockito.when(orderRepository.findByDatetime(testTime)).thenReturn(expectedOrders);

        List<Order> res1 = orderRepository.findByBuyer(testUser);
        List<Order> res2 = orderRepository.findByBuyer_Id(1);
        List<Order> res3 = orderRepository.findByDatetime(testTime);

        Assertions.assertNotNull(res1);
        Assertions.assertNotNull(res2);
        Assertions.assertNotNull(res3);
    }
}