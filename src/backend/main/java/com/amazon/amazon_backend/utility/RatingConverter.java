package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.model.Rating;

import java.math.BigDecimal;

public class RatingConverter {
    public static RatingResponse toRatingResponse(Rating rating, BigDecimal newAverageRating, Long totalRatingsCount){
        if(rating==null){
            return null;
        }

        return RatingResponse.builder()
                .ratingId(rating.getRating_Id())
                .userId(rating.getUser().getId())
                .productId(rating.getProduct().getProductId())
                .stars(rating.getStars())
                .createdAt(rating.getCreated_At())
                .newAverageRating(newAverageRating)
                .totalRatingsCount(totalRatingsCount)
                .build();
    }
}