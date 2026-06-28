package com.amazon.amazon_backend.dto;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.User;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Integer productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private List<String> imageUrls;
    private String categoryName;
    private String sellerName;
}
