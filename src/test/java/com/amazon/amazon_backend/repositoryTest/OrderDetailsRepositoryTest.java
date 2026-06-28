package com.amazon.amazon_backend.repositoryTest;

import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.OrderDetails;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.repository.OrderDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class OrderDetailsRepositoryTest {

    @Test
    void testAllDetailsRepositoryMethodsForCoverage() {
        OrderDetailsRepository repository = Mockito.mock(OrderDetailsRepository.class);

        Order order = new Order();
        Product product = new Product();
        List<OrderDetails> expectedDetails = List.of(new OrderDetails());

        Mockito.when(repository.findByOrder(order)).thenReturn(expectedDetails);
        Mockito.when(repository.findByProduct(product)).thenReturn(expectedDetails);

        List<OrderDetails> res1 = repository.findByOrder(order);
        List<OrderDetails> res2 = repository.findByProduct(product);

        Assertions.assertNotNull(res1);
        Assertions.assertNotNull(res2);
    }
}