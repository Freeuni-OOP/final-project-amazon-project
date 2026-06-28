package com.amazon.amazon_backend.dto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class ProductUpdateRequests {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PriceUpdateRequest{
        private BigDecimal price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuantityUpdateRequest{
        private Integer quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImagesUpdateRequest{
        @Size(max = 5, message = "You can upload up to 5 images for a product")
        private List<String> imageUrls;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameDescriptionUpdateRequest {
        private String productName;
        private String description;
    }
}
