package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Comment;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
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
        if (commentRepository.count() > 0) return;

        var allProducts = productRepository.findAll();
        var allUsers = userRepository.findAll();

        if (!allProducts.isEmpty() && !allUsers.isEmpty()) {

            Product sampleProduct = allProducts.get(0);
            User sampleUser = allUsers.get(0);

            List<String> commentsText = List.of(
                    "Great quality for the price! Will definitely buy again.",
                    "Fits perfectly, but the color is slightly darker.",
                    "Super good quality, highly recommend!",
                    "Decent product, took a bit long to arrive.",
                    "Best purchase I have made this year!"
            );

            for (String text : commentsText) {
                Comment comment = new Comment();
                comment.setUser(sampleUser);
                comment.setProduct(sampleProduct);
                comment.setCommentStr(text);

                commentRepository.save(comment);

                Thread.sleep(10);
            }
        }
        else {
            System.out.println("Error seeding comments");
        }
    }

}