package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.RatingRequest;
import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.RatingRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RatingService ratingService;

    private User sampleUser;
    private Product sampleProduct;
    private RatingRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1);

        sampleProduct = new Product();
        sampleProduct.setProductId(100);
        sampleProduct.setAverageRating(BigDecimal.ZERO);

        sampleRequest = new RatingRequest();
        sampleRequest.setUserId(1);
        sampleRequest.setProductId(100);
        sampleRequest.setStars(5);
    }

    @Test
    @DisplayName("Should successfully create a new rating when no existing rating is found")
    void shouldCreateNewRating() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(productRepository.findById(100)).thenReturn(Optional.of(sampleProduct));
        when(ratingRepository.findByUser_IdAndProduct_ProductId(sampleUser.getId(), sampleProduct.getProductId())).thenReturn(Optional.empty());

        Rating savedRating = new Rating(sampleUser, sampleProduct, 5, LocalDateTime.now());
        savedRating.setRatingId(77);
        when(ratingRepository.save(any(Rating.class))).thenReturn(savedRating);

        when(ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId())).thenReturn(5.0);

        List<Rating> productRatingsList = new ArrayList<>();
        productRatingsList.add(savedRating);
        when(ratingRepository.findByProduct_ProductId(sampleProduct.getProductId())).thenReturn(productRatingsList);

        // When
        RatingResponse response = ratingService.addOrUpdateRating(sampleRequest);

        // Then
        assertNotNull(response);
        assertEquals(77, response.getRatingId());
        assertEquals(new BigDecimal("5.00"), response.getNewAverageRating());
        assertEquals(1L, response.getTotalRatingsCount());

        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    @DisplayName("Should successfully update an existing rating")
    void shouldUpdateExistingRating() {
        // Given
        Rating existingRating = new Rating(sampleUser, sampleProduct, 3, LocalDateTime.now().minusDays(1));
        existingRating.setRatingId(77);

        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(productRepository.findById(100)).thenReturn(Optional.of(sampleProduct));
        when(ratingRepository.findByUser_IdAndProduct_ProductId(sampleUser.getId(), sampleProduct.getProductId())).thenReturn(Optional.of(existingRating));

        when(ratingRepository.save(existingRating)).thenReturn(existingRating);
        when(ratingRepository.calculateAverageRatingByProduct(sampleProduct.getProductId())).thenReturn(5.0);

        // When
        RatingResponse response = ratingService.addOrUpdateRating(sampleRequest);

        // Then
        assertNotNull(response);
        assertEquals(5, existingRating.getStars(), "The existing rating stars should be updated to 5");
        assertEquals(new BigDecimal("5.00"), response.getNewAverageRating());

        verify(ratingRepository, times(1)).save(existingRating);
        verify(productRepository, times(1)).save(sampleProduct);
    }

    @Test
    @DisplayName("Should throw RuntimeException when user is not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.addOrUpdateRating(sampleRequest);
        });

        assertEquals("User not found with ID:1", exception.getMessage());
        verify(productRepository, never()).findById(anyInt());
        verify(ratingRepository, never()).save(any(Rating.class));
    }

    @Test
    @DisplayName("Should throw RuntimeException when product is not found")
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(sampleUser));
        when(productRepository.findById(100)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ratingService.addOrUpdateRating(sampleRequest);
        });

        assertEquals("Product not found with ID:100", exception.getMessage());
        verify(ratingRepository, never()).findByUser_IdAndProduct_ProductId(any(), any());
        verify(ratingRepository, never()).save(any(Rating.class));
    }
}