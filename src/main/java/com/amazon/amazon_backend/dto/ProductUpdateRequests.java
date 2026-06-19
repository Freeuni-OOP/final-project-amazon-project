package com.amazon.amazon_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    public static class ImageUpdateRequest{
        private String imgUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameDescriptionUpdateRequest {
        private String productName;
        private String description;
    }
}
