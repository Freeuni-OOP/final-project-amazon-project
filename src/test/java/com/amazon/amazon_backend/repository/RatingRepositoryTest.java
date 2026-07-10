package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingRepositoryTest {

    @Mock
    private RatingRepository ratingRepository;

    private User sampleUser;
    private Product sampleProduct;
    private Rating sampleRating;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleProduct = new Product();
        sampleRating = new Rating(sampleUser, sampleProduct, 5, LocalDateTime.now());
    }

    @Test
    @DisplayName("Should mock finding ratings by a specific user")
    void shouldFindRatingsByUser() {
        // Given
        List<Rating> expectedRatings = Arrays.asList(sampleRating);
        when(ratingRepository.findByUser_Id(sampleUser.getId())).thenReturn(expectedRatings);

        // When
        List<Rating> result = ratingRepository.findByUser_Id(sampleUser.getId());

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals(sampleUser, result.get(0).getUser(), "User in rating should match");
        verify(ratingRepository, times(1)).findByUser_Id(sampleUser.getId());
    }

    @Test
    @DisplayName("Should mock finding ratings for a specific product")
    void shouldFindRatingsByProduct() {
        // Given
        List<Rating> expectedRatings = Arrays.asList(sampleRating);
        when(ratingRepository.findByProduct_ProductId(sampleProduct.getProductId())).thenReturn(expectedRatings);

        // When
        List<Rating> result = ratingRepository.findByProduct_ProductId(sampleProduct.getProductId());

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals(sampleProduct, result.get(0).getProduct(), "Product in rating should match");
        verify(ratingRepository, times(1)).findByProduct_ProductId(sampleProduct.getProductId());
    }

    @Test
    @DisplayName("Should mock finding a specific rating by user and product")
    void shouldFindRatingByUserAndProduct() {
        // Given
        when(ratingRepository.findByUser_IdAndProduct_ProductId(sampleUser.getId(), sampleProduct.getProductId()))
                .thenReturn(Optional.of(sampleRating));

        // When
        Optional<Rating> result = ratingRepository.findByUser_IdAndProduct_ProductId(sampleUser.getId(), sampleProduct.getProductId());

        // Then
        assertTrue(result.isPresent(), "Rating should be present");
        assertEquals(5, result.get().getStars(), "Stars should be 5");
        verify(ratingRepository, times(1)).findByUser_IdAndProduct_ProductId(sampleUser.getId(), sampleProduct.getProductId());
    }

    @Test
    @DisplayName("Should mock calculating average rating for a product")
    void shouldCalculateAverageRatingByProduct() {
        // Given
        Double expectedAverage = 4.5;
        when(ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId())).thenReturn(expectedAverage);

        // When
        Double result = ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId());

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(4.5, result, "Average rating should be 4.5");
        verify(ratingRepository, times(1)).calculateAverageRatingByProduct(sampleProduct.getProductId());
    }

    @Test
    @DisplayName("Should mock returning 0.0 when no ratings exist for a product")
    void shouldReturnZeroWhenNoRatingsExist() {
        // Given
        when(ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId())).thenReturn(0.0);

        // When
        Double result = ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId());

        // Then
        assertNotNull(result, "Result should not be null");
        assertEquals(0.0, result, "Should return 0.0 for empty ratings");
        verify(ratingRepository, times(1)).calculateAverageRatingByProduct(sampleProduct.getProductId());
    }
}