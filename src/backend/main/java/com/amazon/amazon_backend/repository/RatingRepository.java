package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByUser(User user);
    List<Rating> findByProduct(Product product);
    Optional<Rating> findByUserAndProduct(User user, Product product);

    @Query("SELECT COALESCE(AVG(r.stars), 0.0) FROM Rating r WHERE r.product = :product")
    Double calculateAverageRatingByProduct(@Param("product") Product product);
}
