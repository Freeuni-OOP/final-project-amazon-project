package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.Rating;
import com.amazon.amazon_backend.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(6)
public class RatingRepositorySeeder implements CommandLineRunner {

    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public RatingRepositorySeeder(RatingRepository ratingRepository,
                                  ProductRepository productRepository,
                                  UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
            List<Product> allProducts = productRepository.findAll();
            List<User> allUsers = userRepository.findAll();

            if (allProducts != null && !allProducts.isEmpty() && allUsers != null && !allUsers.isEmpty()) {

                Product sampleProduct = allProducts.get(0);
                User sampleUser = allUsers.get(0);


                ratingRepository.save(new Rating(sampleUser, sampleProduct,5, LocalDateTime.now()));
                ratingRepository.save(new Rating(sampleUser, sampleProduct,3, LocalDateTime.now()));
                ratingRepository.save(new Rating(sampleUser, sampleProduct,4, LocalDateTime.now()));

            } else {
                System.out.println("Error seeding ratings");
            }

    }
}