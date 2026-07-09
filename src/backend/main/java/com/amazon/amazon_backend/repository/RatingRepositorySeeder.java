package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Order(2)
public class RatingRepositorySeeder implements CommandLineRunner {

    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;

    public RatingRepositorySeeder(RatingRepository ratingRepository, ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.existsById(1)) {
            Product sampleProduct = productRepository.findById(1).orElse(null);

            if (ratingRepository.count() == 0) {
                ratingRepository.save(new Rating(null, sampleProduct, 5, LocalDateTime.now()));
                ratingRepository.save(new Rating(null, sampleProduct, 4, LocalDateTime.now()));
                ratingRepository.save(new Rating(null, sampleProduct, 4, LocalDateTime.now()));

            }
        }
    }
}