package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(4)
public class CartItemsSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    CartItemsSeeder(UserRepository userRepository, ProductRepository productRepository, CartItemRepository cartItemRepository){
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cartItemRepository.count() > 0) {
            log.info("CartItems already exist ({} rows), skipping seed.", cartItemRepository.count());
            return;
        }

        log.info("Seeding sample CartItems...");

        List<Product> availableProducts = productRepository.findAll();
        User amazonBot = userRepository.findById(1).get();

        Product product1 = availableProducts.get(0);
        Product product2 = availableProducts.get(1);
        Product product3 = availableProducts.get(2);
        Product product4 = availableProducts.get(3);
        Product product5 = availableProducts.get(4);

        product1.setQuantity(4);
        product2.setQuantity(120);
        product3.setQuantity(30);
        product4.setQuantity(19);
        product5.setQuantity(200);

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);

        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(amazonBot);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(amazonBot);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(10);

        CartItem cartItem3 = new CartItem();
        cartItem3.setUser(amazonBot);
        cartItem3.setProduct(product3);
        cartItem3.setQuantity(1);

        CartItem cartItem4 = new CartItem();
        cartItem4.setUser(amazonBot);
        cartItem4.setProduct(product4);
        cartItem4.setQuantity(5);

        CartItem cartItem5 = new CartItem();
        cartItem5.setUser(amazonBot);
        cartItem5.setProduct(product5);
        cartItem5.setQuantity(5);

        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);
        cartItemRepository.save(cartItem3);
        cartItemRepository.save(cartItem4);
        cartItemRepository.save(cartItem5);
    }
}
