package com.amazon.amazon_backend.repository;

import com.amazon.amazon_backend.model.*;
import com.amazon.amazon_backend.repository.CartItemRepository;
import com.amazon.amazon_backend.repository.CategoryRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemRepositoryTest {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User user;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        User validUser = new User();
        validUser.setEmail("testuser@example.com");
        validUser.setUsername("testuser_123");
        validUser.setPassEncrypted("dummy_encrypted_password");
        validUser.setBirthDate(new java.util.Date());
        validUser.setBalance(BigDecimal.valueOf(1000.00));

        user = userRepository.save(validUser);

        Category category = categoryRepository.save(new Category("Toys"));
        product1 = productRepository.save(new Product("Pink fluffy toy for kids.", user, category, "Labubu", BigDecimal.valueOf(19.99), 10, null));
        product2 = productRepository.save(new Product("Purple cute toy for babies.", user, category, "Lafufu", BigDecimal.valueOf(29.99), 12, null));
    }


    @Test
    public void testCartItemRepositoryOnIds() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        CartItem savedCartItem1 = cartItemRepository.save(cartItem1);
        assertNotNull(savedCartItem1.getId());

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        CartItem savedCartItem2 = cartItemRepository.save(cartItem2);
        assertNotNull(savedCartItem2.getId());
    }

    @Test
    public void testCartItemRepositoryOnQuantities() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(5);
        CartItem savedCartItem1 = cartItemRepository.save(cartItem1);
        assertEquals(5, savedCartItem1.getQuantity());

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(3);
        CartItem savedCartItem2 = cartItemRepository.save(cartItem2);
        assertEquals(3, savedCartItem2.getQuantity());
    }

    @Test
    public void testCartItemRepositoryOnCount() {
        assertEquals(0, cartItemRepository.count());

        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItemRepository.save(cartItem1);
        assertEquals(1, cartItemRepository.count());

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItemRepository.save(cartItem2);
        assertEquals(2, cartItemRepository.count());
    }

    @Test
    public void testCartItemRepositoryOnDelete() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        CartItem savedCartItem1 = cartItemRepository.save(cartItem1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        CartItem savedCartItem2 = cartItemRepository.save(cartItem2);

        assertEquals(2, cartItemRepository.count());

        cartItemRepository.deleteById(savedCartItem1.getId());
        assertEquals(1, cartItemRepository.count());

        cartItemRepository.deleteById(savedCartItem2.getId());
        assertEquals(0, cartItemRepository.count());
    }

    @Test
    public void testFindByUser_Id() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItemRepository.save(cartItem1);

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItemRepository.save(cartItem2);

        List<CartItem> results = cartItemRepository.findByUser_Id(user.getId());
        assertEquals(2, results.size());
    }

    @Test
    public void testFindByProduct_ProductId() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItemRepository.save(cartItem1);

        List<CartItem> results = cartItemRepository.findByProduct_ProductId(product1.getProductId());
        assertEquals(1, results.size());
        assertEquals(2, results.get(0).getQuantity());
    }

    @Test
    public void testFindByUser_IdAndProduct_ProductId() {
        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItemRepository.save(cartItem1);

        Optional<CartItem> result = cartItemRepository.findByUser_IdAndProduct_ProductId(user.getId(), product1.getProductId());
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getQuantity());

        Optional<CartItem> notFound = cartItemRepository.findByUser_IdAndProduct_ProductId(user.getId(), product2.getProductId());
        assertFalse(notFound.isPresent());
    }

    @Test
    public void testCountByUser_Id() {
        assertEquals(0, cartItemRepository.countByUser_Id(user.getId()));

        CartItem cartItem1 = new CartItem();
        cartItem1.setUser(user);
        cartItem1.setProduct(product1);
        cartItem1.setQuantity(2);
        cartItemRepository.save(cartItem1);

        assertEquals(1, cartItemRepository.countByUser_Id(user.getId()));

        CartItem cartItem2 = new CartItem();
        cartItem2.setUser(user);
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItemRepository.save(cartItem2);

        assertEquals(2, cartItemRepository.countByUser_Id(user.getId()));
    }

}
