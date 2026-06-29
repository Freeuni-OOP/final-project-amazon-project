package com.amazon.amazon_backend.repository;
import com.amazon.amazon_backend.Item;
import com.amazon.amazon_backend.ItemData;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Component
public class ProductRepositorySeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public ProductRepositorySeeder(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, ImageRepository imageRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() > 0) {
            log.info("Products already exist ({} rows), skipping seed.", productRepository.count());
            return;
        }

        log.info("Seeding sample products...");

        Optional<User> list = userRepository.findByUsername("AmazonBot");
        User seller = list.get();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File file = new File("src/backend/main/resources/products.json");
        ItemData data = objectMapper.readValue(file, ItemData.class);

        for (Item product : data.getProducts()) {
            if (product.getQuantity() == 0) {
                product.setQuantity(1);
            }

            Category category;
            Optional<Category> categories = categoryRepository.findByCategoryName(product.getCategory());
            if (!categories.isPresent()) {
                product.setCategory("Other");
                categories = categoryRepository.findByCategoryName(product.getCategory());
            }
            category = categories.get();

            Product newProduct = Product.builder()
                    .productName(product.getProductName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .seller(seller)
                    .category(category)
                    .images(new ArrayList<>())
                    .build();

            Product savedProduct = productRepository.save(newProduct);

            ArrayList<Image> imagesForNewProduct = new ArrayList<>();
            for(int i = 0; i < product.getImages().length; i++){
                Image newImage = new Image(newProduct, product.getImages()[i]);
                imagesForNewProduct.add(newImage);
                imageRepository.save(newImage);
            }
            savedProduct.setImages(imagesForNewProduct);
        }
    }

}
