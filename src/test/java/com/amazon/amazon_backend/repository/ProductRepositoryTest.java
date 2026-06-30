package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CategoryRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private Category category;
    private User seller;

    @BeforeEach
    public void setUp() {
        category = categoryRepository.save(new Category("Electronics"));

        User user = new User();
        user.setEmail("test@email.com");
        user.setUsername("sellerUser");
        user.setPassEncrypted("pass");
        user.setBirthDate(new java.util.Date());
        user.setBalance(BigDecimal.valueOf(1000.00));

        seller = userRepository.save(user);
    }

    private Product buildProduct(User productSeller, Category productCategory, String name, BigDecimal price, Integer quantity, String imageUrl) {
        Product product = new Product("Description for " + name, productSeller, productCategory, name, price, quantity, new ArrayList<>());

        Image image = new Image(null, imageUrl);
        image.setProduct(product);
        product.getImages().add(image);

        return product;
    }

    @Test
    public void testProductRepositoryOnIds() {
        Product product1 = buildProduct(seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, "/photos/laptop.png");
        Product savedProduct1 = productRepository.save(product1);
        assertNotNull(savedProduct1.getProductId());

        Product product2 = buildProduct(seller, category, "Phone", BigDecimal.valueOf(599.99), 20, "/photos/phone.png");
        Product savedProduct2 = productRepository.save(product2);
        assertNotNull(savedProduct2.getProductId());
    }

    @Test
    public void testProductRepositoryOnProductNames() {
        Product product1 = buildProduct(seller, category, "Headphones", BigDecimal.valueOf(99.99), 30, "/photos/headphones.png");
        Product savedProduct1 = productRepository.save(product1);
        assertEquals("Headphones", savedProduct1.getProductName());

        Product product2 = buildProduct(seller, category, "Monitor", BigDecimal.valueOf(199.99), 12, "/photos/monitor.png");
        Product savedProduct2 = productRepository.save(product2);
        assertEquals("Monitor", savedProduct2.getProductName());
    }

    @Test
    public void testFindByProductNameContainingIgnoreCase() {
        productRepository.save(buildProduct(seller, category, "Gaming Laptop1", BigDecimal.valueOf(1299.99), 5, "/photos/laptop1.png"));
        productRepository.save(buildProduct(seller, category, "Office Laptop1", BigDecimal.valueOf(899.99), 8, "/photos/laptop2.png"));
        productRepository.save(buildProduct(seller, category, "Smartphone", BigDecimal.valueOf(699.99), 25, "/photos/phone.png"));

        List<Product> results = productRepository.findByProductNameContainingIgnoreCase("laptop1");
        assertEquals(2, results.size());
    }

    @Test
    public void testFindByCategory_CategoryId() {
        Category otherCategory = categoryRepository.save(new Category("Sport"));

        productRepository.save(buildProduct(seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, "/photos/laptop.png"));
        productRepository.save(buildProduct(seller, otherCategory, "Football", BigDecimal.valueOf(29.99), 15, "/photos/football.png"));

        List<Product> results = productRepository.findByCategory_CategoryId(category.getCategoryId());
        assertEquals(1, results.size());
        assertEquals("Laptop", results.get(0).getProductName());
    }

    @Test
    public void testFindByCategory_CategoryNameIgnoreCase() {
        productRepository.save(buildProduct(seller, category, "Laptop1", BigDecimal.valueOf(999.99), 10, "/photos/laptop.png"));

        List<Product> results = productRepository.findByCategory_CategoryNameIgnoreCase("electronics");
        assertEquals(1, results.size());
        assertEquals("Laptop1", results.get(0).getProductName());
    }

    @Test
    public void testFindBySeller_Id() {
        User otherSellerUser = new User();
        otherSellerUser.setEmail("otherseller@email.com");
        otherSellerUser.setUsername("otherSellerUser");
        otherSellerUser.setPassEncrypted("pass");
        otherSellerUser.setBirthDate(new java.util.Date());
        otherSellerUser.setBalance(BigDecimal.valueOf(500.00));

        User otherSeller = userRepository.save(otherSellerUser);

        productRepository.save(buildProduct(seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, "/photos/laptop.png"));
        productRepository.save(buildProduct(otherSeller, category, "Phone", BigDecimal.valueOf(599.99), 20, "/photos/phone.png"));

        List<Product> results = productRepository.findBySeller_Id(seller.getId());
        assertEquals(1, results.size());
        assertEquals("Laptop", results.get(0).getProductName());
    }
}