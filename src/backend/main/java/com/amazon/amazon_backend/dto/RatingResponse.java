package com.amazon.amazon_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Integer ratingId;
    private Integer userId;
    private Integer productId;
    private Integer stars;
    private LocalDateTime createdAt;
    private BigDecimal newAverageRating;
    private Long totalRatingsCount;
}
