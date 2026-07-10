package com.amazon.amazon_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private List<String> imageUrls;
    private Integer quantity;
}
