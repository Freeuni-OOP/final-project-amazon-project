package com.amazon.amazon_backend.controller;


import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrdersById(@PathVariable Integer userId){
        return orderService.getOrders(userId);
    }

    @PostMapping("/create/{userId}")
    public OrderResponse createOrder(@PathVariable Integer userId){
        return orderService.createOrder(userId);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderDetails(@PathVariable Integer orderId) {

        return orderService.getOrderById(orderId);
    }

    @GetMapping("/sold/{userId}")
    public List<OrderResponse> getSoldItems(@PathVariable Integer userId) {
     return orderService.getSoldOrders(userId);
    }
}
