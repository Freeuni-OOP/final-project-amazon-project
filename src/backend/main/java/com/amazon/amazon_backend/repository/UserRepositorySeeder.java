package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepositorySeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            log.info("AmazonBot already exist ({} rows), skipping seed.", userRepository.count());
            return;
        }

        log.info("Seeding AmazonBot");

        BigDecimal balance = new BigDecimal(1000000);
        Date date = new Date();
        User amazonBot = new User("AmazonBot@gmail.com", balance, null, date, "AmazonBot", "AmazonBotPassword");
        userRepository.save(amazonBot);
    }

}
