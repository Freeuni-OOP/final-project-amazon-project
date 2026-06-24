package com.amazon.amazon_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Integer userId;
    private List<OrderItemRequest> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequest {
        private Integer productId;
        private Integer quantity;
    }
}