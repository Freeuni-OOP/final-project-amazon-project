package com.amazon.amazon_backend.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    @DisplayName("Should create Rating object with parameterized constructor and set fields correctly")
    void constructorShouldSetAllFieldsCorrectly() {
        // Given
        User user = new User();
        Product product = new Product();
        Integer stars = 5;
        LocalDateTime now = LocalDateTime.now();

        // When
        Rating rating = new Rating(user, product, stars, now);

        // Then
        assertAll("Constructor Fields Validation",
                () -> assertNull(rating.getRatingId(), "Newly created object ID should be null"),
                () -> assertEquals(user, rating.getUser(), "User should match the constructor argument"),
                () -> assertEquals(product, rating.getProduct(), "Product should match the constructor argument"),
                () -> assertEquals(stars, rating.getStars(), "Stars should match the constructor argument"),
                () -> assertEquals(now, rating.getCreatedAt(), "CreatedAt timestamp should match the constructor argument")
        );
    }

    @Test
    @DisplayName("Should successfully set and get all fields using Lombok annotations and NoArgsConstructor")
    void gettersAndSettersShouldWorkPerfectlly() {
        // Given
        Rating rating = new Rating();
        User user = new User();
        Product product = new Product();
        LocalDateTime now = LocalDateTime.now();

        // When & Then
        rating.setRatingId(1);
        assertEquals(1, rating.getRatingId(), "Rating ID getter or setter failed");

        rating.setUser(user);
        assertEquals(user, rating.getUser(), "User getter or setter failed");

        rating.setProduct(product);
        assertEquals(product, rating.getProduct(), "Product getter or setter failed");

        rating.setStars(3);
        assertEquals(3, rating.getStars(), "Stars getter or setter failed");

        rating.setCreatedAt(now);
        assertEquals(now, rating.getCreatedAt(), "CreatedAt getter or setter failed");
    }

    @Test
    @DisplayName("Should accept null values in constructor and setter methods without throwing exceptions")
    void shouldAcceptNullValuesInFields() {
        // Given & When
        Rating ratingFromConstructor = new Rating(null, null, null, null);
        Rating ratingFromSetters = new Rating();

        ratingFromSetters.setRatingId(null);
        ratingFromSetters.setUser(null);
        ratingFromSetters.setProduct(null);
        ratingFromSetters.setStars(null);
        ratingFromSetters.setCreatedAt(null);

        // Then
        assertAll("Null checks for Constructor",
                () -> assertNull(ratingFromConstructor.getUser(), "User field should be null"),
                () -> assertNull(ratingFromConstructor.getProduct(), "Product field should be null"),
                () -> assertNull(ratingFromConstructor.getStars(), "Stars field should be null"),
                () -> assertNull(ratingFromConstructor.getCreatedAt(), "CreatedAt field should be null")
        );

        assertAll("Null checks for Setters",
                () -> assertNull(ratingFromSetters.getRatingId(), "Rating ID should be null"),
                () -> assertNull(ratingFromSetters.getUser(), "User field should be null"),
                () -> assertNull(ratingFromSetters.getProduct(), "Product field should be null"),
                () -> assertNull(ratingFromSetters.getStars(), "Stars field should be null"),
                () -> assertNull(ratingFromSetters.getCreatedAt(), "CreatedAt field should be null")
        );
    }
}