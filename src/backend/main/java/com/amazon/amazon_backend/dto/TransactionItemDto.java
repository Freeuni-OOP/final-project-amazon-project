package com.amazon.amazon_backend.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class TransactionItemDto {
    private Integer productId;
    private String productName;
    private BigDecimal priceAtPurchase;
    private Integer quantityPurchased;
}
