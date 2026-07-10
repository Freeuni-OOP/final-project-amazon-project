package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.RatingRequest;
import com.amazon.amazon_backend.dto.RatingResponse;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.RatingRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import com.amazon.amazon_backend.utility.RatingConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public RatingResponse addOrUpdateRating(RatingRequest ratingRequest){
        User user = userRepository.findById(ratingRequest.getUserId())
                .orElseThrow(()->new RuntimeException("User not found with ID:"+ratingRequest.getUserId()));

        Product product = productRepository.findById(ratingRequest.getProductId())
                .orElseThrow(()->new RuntimeException("Product not found with ID:"+ratingRequest.getProductId()));

        Optional<Rating> existingRatings=ratingRepository.findByUser_IdAndProduct_ProductId(user.getId(), product.getProductId());

        Rating rating;
        if(existingRatings.isPresent()){
            rating=existingRatings.get();
            rating.setStars(ratingRequest.getStars());
            rating.setCreatedAt(LocalDateTime.now());
        }else{
            rating=new Rating(user, product, ratingRequest.getStars(), LocalDateTime.now());
        }

        Rating savedRating=ratingRepository.save(rating);

        Double avg=ratingRepository.calculateAverageRatingByProduct(product.getProductId());

        BigDecimal newAverage=BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);

        product.setAverageRating(newAverage);
        productRepository.save(product);

        long totalRatingsCount=ratingRepository.findByProduct_ProductId(product.getProductId()).size();

        return RatingConverter.toRatingResponse(savedRating, newAverage, totalRatingsCount);
    }
}
