package com.amazon.amazon_backend.dto;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.User;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imgUrl;
    private String categoryName;
    private String sellerName;
}
