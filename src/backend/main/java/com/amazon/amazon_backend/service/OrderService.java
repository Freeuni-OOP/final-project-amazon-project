package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.OrderDetails;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CartItemRepository;
import com.amazon.amazon_backend.repository.OrderRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import com.amazon.amazon_backend.utility.OrderConverter;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<OrderResponse> getOrders(Integer userId){
        return orderRepository.findByBuyer_Id(userId).stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse createOrder(Integer userId){

        User user=userRepository.findById(userId)
                .orElseThrow(()->new NoSuchElementException("No user was found."));

        List<CartItem> items=cartItemRepository.findByUser_Id(userId);

        if(items.isEmpty()){
            throw new IllegalStateException("The cart is empty");
        }

        Order order=new Order();
        order.setBuyer(user);

        List<OrderDetails> detailsList=items.stream().map(cartItem -> {
            OrderDetails details=new OrderDetails();
            details.setOrder(order);
            details.setProduct(cartItem.getProduct());
            details.setQuantity(cartItem.getQuantity());

            BigDecimal price=cartItem.getProduct().getPrice();
            BigDecimal amount=price.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            details.setAmount(amount);

            return details;
        }).collect(Collectors.toList());

        order.setOrderDetails(detailsList);

        order.setDatetime(LocalDateTime.now());

        Order savedOrder= orderRepository.save(order);

        cartItemRepository.deleteAll(items);

        return OrderConverter.toOrderResponse(savedOrder);
    }
}