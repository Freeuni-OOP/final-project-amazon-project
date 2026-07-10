package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class CommentRepositorySeeder implements CommandLineRunner {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CommentRepositorySeeder(CommentRepository commentRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var allProducts = productRepository.findAll();
        var allUsers = userRepository.findAll();

        if (!allProducts.isEmpty() && !allUsers.isEmpty()) {

            Product sampleProduct = allProducts.get(0);
            User sampleUser = allUsers.get(0);

            commentRepository.save(new Comment("Great quality for the price! Will definitely buy again.", sampleProduct, sampleUser));
            commentRepository.save(new Comment("Fits perfectly, but the color is slightly darker.", sampleProduct, sampleUser));
            commentRepository.save(new Comment("Super soft material, highly recommend!", sampleProduct, sampleUser));
            commentRepository.save(new Comment("Decent product, took a bit long to arrive.", sampleProduct, sampleUser));
        } else {
            System.out.println("Error seeding comments");
        }
    }

}