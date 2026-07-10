package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.OrderDetails;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@org.springframework.core.annotation.Order(5)
@RequiredArgsConstructor
public class OrderRepositorySeeder implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {

        List<User> allUsers = userRepository.findAll();
        List<Product> allProducts = productRepository.findAll();

        if (allUsers.size() < 2 || allProducts.size() < 4) {
            log.error("Seeder needs at least 2 users and 4 products in the database");
            return;
        }
        if(orderRepository.findAll().size()>1)return;
        User botUser = allUsers.stream()
                .filter(u -> u.getUsername().toLowerCase().contains("bot"))
                .findFirst()
                .orElse(allUsers.get(0));

        User otherUser = allUsers.stream()
                .filter(u -> !u.getId().equals(botUser.getId()))
                .findFirst()
                .orElse(allUsers.get(1));

        Product p1 = allProducts.get(0);
        Product p2 = allProducts.get(1);
        Product p3 = allProducts.get(2);
        Product p4 = allProducts.get(3);

        p1.setSeller(otherUser);
        p2.setSeller(otherUser);
        p3.setSeller(botUser);
        p4.setSeller(botUser);


        Order order1 = new Order();
        order1.setBuyer(botUser);
        order1.setDatetime(LocalDateTime.now().minusDays(1));
        order1.setTotalAmount(p1.getPrice().multiply(BigDecimal.valueOf(1)));

        OrderDetails detail1 = new OrderDetails();
        detail1.setOrder(order1);
        detail1.setProduct(p1);
        detail1.setQuantity(1);
        detail1.setAmount(order1.getTotalAmount());

        order1.setOrderDetails(List.of(detail1));
        orderRepository.save(order1);


        Order order2 = new Order();
        order2.setBuyer(botUser);
        order2.setDatetime(LocalDateTime.now().minusDays(2));
        order2.setTotalAmount(p2.getPrice().multiply(BigDecimal.valueOf(2)));

        OrderDetails detail2 = new OrderDetails();
        detail2.setOrder(order2);
        detail2.setProduct(p2);
        detail2.setQuantity(2);
        detail2.setAmount(order2.getTotalAmount());

        order2.setOrderDetails(List.of(detail2));
        orderRepository.save(order2);



        Order order3 = new Order();
        order3.setBuyer(otherUser);
        order3.setDatetime(LocalDateTime.now().minusDays(3));
        order3.setTotalAmount(p3.getPrice().multiply(BigDecimal.valueOf(1)));

        OrderDetails detail3 = new OrderDetails();
        detail3.setOrder(order3);
        detail3.setProduct(p3);
        detail3.setQuantity(1);
        detail3.setAmount(order3.getTotalAmount());

        order3.setOrderDetails(List.of(detail3));
        orderRepository.save(order3);


        Order order4 = new Order();
        order4.setBuyer(otherUser);
        order4.setDatetime(LocalDateTime.now().minusDays(4));
        order4.setTotalAmount(p4.getPrice().multiply(BigDecimal.valueOf(3)));

        OrderDetails detail4 = new OrderDetails();
        detail4.setOrder(order4);
        detail4.setProduct(p4);
        detail4.setQuantity(3);
        detail4.setAmount(order4.getTotalAmount());

        order4.setOrderDetails(List.of(detail4));
        orderRepository.save(order4);


        log.info("orders added successfully");
    }
}