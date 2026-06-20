package com.amazon.amazon_backend.dto;

import com.amazon.amazon_backend.model.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class TransactionResponse {
    private Integer id;
    private Integer orderId;
    private String buyerName;
    private String sellerName;
    private BigDecimal totalAmount;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
