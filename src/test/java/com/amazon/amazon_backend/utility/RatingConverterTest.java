package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingConverterTest {

    @Test
    @DisplayName("Should successfully convert Rating object to RatingResponse")
    void shouldConvertRatingToRatingResponse() {
        User user = new User();
        user.setId(1);

        Product product = new Product();
        product.setProductId(100);

        LocalDateTime now = LocalDateTime.now();
        Rating rating = new Rating(user, product, 5, now);
        rating.setRatingId(12);

        BigDecimal newAverageRating = new BigDecimal("4.50");
        Long totalRatingsCount = 10L;

        // When
        RatingResponse response = RatingConverter.toRatingResponse(rating, newAverageRating, totalRatingsCount);

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(12, response.getRatingId(), "RatingId mapping failed");
        assertEquals(1, response.getUserId(), "UserId mapping failed");
        assertEquals(100, response.getProductId(), "ProductId mapping failed");
        assertEquals(5, response.getStars(), "Stars mapping failed");
        assertEquals(now, response.getCreatedAt(), "CreatedAt mapping failed");
        assertEquals(newAverageRating, response.getNewAverageRating(), "NewAverageRating mapping failed");
        assertEquals(totalRatingsCount, response.getTotalRatingsCount(), "TotalRatingsCount mapping failed");
    }

    @Test
    @DisplayName("Should return null when input rating object is null")
    void shouldReturnNullWhenRatingIsNull() {
        // Given
        Rating rating = null;
        BigDecimal newAverageRating = new BigDecimal("4.50");
        Long totalRatingsCount = 10L;

        // When
        RatingResponse response = RatingConverter.toRatingResponse(rating, newAverageRating, totalRatingsCount);

        // Then
        assertNull(response, "Response should be null when source rating is null");
    }
}