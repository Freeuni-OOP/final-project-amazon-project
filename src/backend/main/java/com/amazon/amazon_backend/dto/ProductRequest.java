package com.amazon.amazon_backend.dto;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Integer sellerId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer categoryId;
    private String categoryName;

    @Size(max = 5, message = "You can upload up to 5 images for a product")
    private List<String> imageUrls;
}
