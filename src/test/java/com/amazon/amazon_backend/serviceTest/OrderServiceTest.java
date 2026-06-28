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

    @Mock
    private ProductRepository productRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_Success_ShouldReturnOrderResponse() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setPrice(BigDecimal.valueOf(100));
        product.setQuantity(10); // ADDED: Must have enough inventory!

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

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(userId);

        Assertions.assertNotNull(response);
        Mockito.verify(cartItemRepository, Mockito.times(1)).deleteAll(cartItems);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any(Transaction.class));
        Mockito.verify(orderDetailsRepository, Mockito.times(1)).saveAll(Mockito.anyList());
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

    @Test
    void createOrder_UserNotFound_ShouldThrowException() {
        Integer userId = 99;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            orderService.createOrder(userId);
        });
    }

    @Test
    void createOrder_NotEnoughInventory_ShouldThrowException() {
        Integer userId = 1;
        User user = new User();

        Product product = new Product();
        product.setProductName("Laptop");
        product.setPrice(BigDecimal.valueOf(100));
        product.setQuantity(1);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(5);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(cartItemRepository.findByUser_Id(userId)).thenReturn(List.of(cartItem));

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(new Order());

        Assertions.assertThrows(IllegalStateException.class, () -> {
            orderService.createOrder(userId);
        });
    }

    @Test
    void getOrders_Success_ShouldReturnOrderResponseList() {
        Integer userId = 1;

        Order existingOrder = new Order();
        existingOrder.setOrderId(50);
        existingOrder.setTotalAmount(BigDecimal.valueOf(150.00));
        existingOrder.setDatetime(java.time.LocalDateTime.now());

        User buyer = new User();
        buyer.setId(userId);
        existingOrder.setBuyer(buyer);

        Mockito.when(orderRepository.findByBuyer_Id(userId)).thenReturn(List.of(existingOrder));

        List<OrderResponse> responses = orderService.getOrders(userId);

        Assertions.assertNotNull(responses);
        Assertions.assertEquals(1, responses.size());
        Mockito.verify(orderRepository, Mockito.times(1)).findByBuyer_Id(userId);
    }
}