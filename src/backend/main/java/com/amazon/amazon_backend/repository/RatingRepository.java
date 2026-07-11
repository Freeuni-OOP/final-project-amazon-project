package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByUser_Id(Integer userId);
    List<Rating> findByProduct_ProductId(Integer productId);
    Optional<Rating> findByUser_IdAndProduct_ProductId(Integer userId, Integer productId);

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.product.id = :productId")
    Double calculateAverageRatingByProduct(@Param("productId") Integer productId);
}
