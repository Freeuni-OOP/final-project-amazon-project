package com.amazon.amazon_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Integer id;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private String imgUrl;
    private Integer quantity;

}
