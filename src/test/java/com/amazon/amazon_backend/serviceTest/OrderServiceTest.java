package com.amazon.amazon_backend.serviceTest;

import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.*;
import com.amazon.amazon_backend.repository.*;
import com.amazon.amazon_backend.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_Success_ShouldReturnOrderResponse() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        List<CartItem> cartItems = List.of(cartItem);

        Order order = new Order();
        order.setBuyer(user);
        order.setTotalAmount(BigDecimal.valueOf(200));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(cartItemRepository.findByUser_Id(userId)).thenReturn(cartItems);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        OrderResponse response = orderService.createOrder(userId);

        Assertions.assertNotNull(response);
        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteAll(cartItems);
    }

    @Test
    void createOrder_CartEmpty_ShouldThrowException() {
        Integer userId = 1;
        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(cartItemRepository.findByUser_Id(userId)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(IllegalStateException.class, () -> {
            orderService.createOrder(userId);
        });
    }
}
