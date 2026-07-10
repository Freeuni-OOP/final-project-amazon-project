package com.amazon.amazon_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer orderId;
    private Integer userId;
    private String username;
    private LocalDateTime dateTime;
    private BigDecimal totalAmount;
    private List<OrderDetailsResponse> items;
}