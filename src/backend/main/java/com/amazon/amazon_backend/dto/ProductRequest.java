package com.amazon.amazon_backend.dto;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Integer sellerId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imgUrl;
    private Integer categoryId;
    private String categoryName;
}
