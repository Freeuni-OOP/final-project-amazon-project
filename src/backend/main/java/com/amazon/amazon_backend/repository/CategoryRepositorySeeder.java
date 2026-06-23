package com.amazon.amazon_backend.repository;
import com.amazon.amazon_backend.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryRepositorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            log.info("Categories already exist ({} rows), skipping seed.", categoryRepository.count());
            return;
        }

        log.info("Seeding sample categories...");

        categoryRepository.saveAll(List.of(
                new Category("Electronics & Gadgets"),
                new Category("Clothing, Shoes & Jewelry"),
                new Category("Entertainment"),
                new Category("Sports & Outdoors"),
                new Category("Beauty"),
                new Category("Furniture"),
                new Category("Other")
        ));

        log.info("Seeded {} categories.", categoryRepository.count());
    }
}
