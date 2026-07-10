package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.*;
import com.amazon.amazon_backend.repository.*;
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

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private TransactionService transactionService;

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


        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setBuyer(user);
        order.setTotalAmount(totalAmount);
        order.setDatetime(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        List<OrderDetails> detailsList=items.stream().map(cartItem -> {
            Product product=cartItem.getProduct();

            if(product.getQuantity()<cartItem.getQuantity()){
                throw new IllegalStateException("Not enough inventory for product: "+product.getProductName());
            }

            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);

            BigDecimal price=cartItem.getProduct().getPrice();

            OrderDetails details=new OrderDetails();
            details.setOrder(savedOrder);
            details.setQuantity(cartItem.getQuantity());
            details.setAmount(price);
            details.setProduct(product);

            return details;
        }).collect(Collectors.toList());

        orderDetailsRepository.saveAll(detailsList);

        transactionService.createTransactionsForOrder(savedOrder, detailsList);

        savedOrder.setOrderDetails(detailsList);
        
        cartItemRepository.deleteAll(items);

        return OrderConverter.toOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order with ID " + orderId + " not found."));

        return OrderConverter.toOrderResponse(order);
    }


    public List<OrderResponse> getSoldOrders(Integer userId) {
        List<Order> soldOrders = orderRepository.findBySellerId(userId);

        return soldOrders.stream()
                .map(OrderConverter::toOrderResponse)
                .collect(Collectors.toList());
    }
}