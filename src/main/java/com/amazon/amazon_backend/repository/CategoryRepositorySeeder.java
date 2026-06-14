package com.amazon.amazon_backend.repository;
import com.amazon.amazon_backend.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j // This gives you access to a standardized logging tool (log.info(...)). It replaces the amateurish System.out.println()
@Component // this tells Spring to initialize all the objects when needed
@RequiredArgsConstructor // creates constructor with parameter categoryRepository
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
                new Category("Other")
        ));

        log.info("Seeded {} categories.", categoryRepository.count());
    }
}
