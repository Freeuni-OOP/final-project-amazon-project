package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CartItemRequest;
import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CartItemRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import com.amazon.amazon_backend.service.CartItemService;
import com.amazon.amazon_backend.utility.CartItemConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private User user;
    private Product product;
    private CartItem cartItem;
    private CartItemResponse response;

    @BeforeEach
    public void setUp() {
        user = new User();
        product = new Product("Description", user, new Category("Electronics"), "Laptop", BigDecimal.valueOf(999.99), 10, null);
        cartItem = new CartItem(1, user, product, 2);
        response = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null, 2);
    }

    @Test
    public void testGetCartItemById() {
        when(cartItemRepository.findById(1)).thenReturn(Optional.of(cartItem));

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponse(cartItem)).thenReturn(response);

            CartItemResponse result = cartItemService.getCartItemById(1);
            assertNotNull(result);
            assertEquals("Laptop", result.getProductName());
        }
    }


    @Test
    public void testSearchCartItemsByUserId() {
        when(cartItemRepository.findByUser_Id(1)).thenReturn(List.of(cartItem));

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponseList(any())).thenReturn(List.of(response));

            List<CartItemResponse> results = cartItemService.searchCartItemsByUserId(1);
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testSearchCartItemsByProductId() {
        when(cartItemRepository.findByProduct_ProductId(1)).thenReturn(List.of(cartItem));

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponseList(any())).thenReturn(List.of(response));

            List<CartItemResponse> results = cartItemService.searchCartItemsByProductId(1);
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testAddCartItem_NewItem() {
        CartItemRequest request = new CartItemRequest(1, 1, 2);

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.empty());
        when(cartItemRepository.countByUser_Id(1)).thenReturn(0L);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponse(any())).thenReturn(response);

            CartItemResponse result = cartItemService.addCartItem(request);
            assertNotNull(result);
            assertEquals(2, result.getQuantity());
        }
    }

    @Test
    public void testAddCartItem_ExistingItemIncrementsQuantity() {
        CartItemRequest request = new CartItemRequest(1, 1, 3);

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItemResponse updatedResponse = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null, 5);

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponse(cartItem)).thenReturn(updatedResponse);

            CartItemResponse result = cartItemService.addCartItem(request);
            assertEquals(5, cartItem.getQuantity());
            assertEquals(5, result.getQuantity());
        }
    }

    @Test
    public void testAddCartItem_DefaultsQuantityToOneWhenNull() {
        CartItemRequest request = new CartItemRequest(1, 1, null);

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.empty());
        when(cartItemRepository.countByUser_Id(1)).thenReturn(0L);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any(CartItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponse(any(CartItem.class)))
                    .thenAnswer(invocation -> {
                        CartItem item = invocation.getArgument(0);
                        return new CartItemResponse(item.getId(), 1, "Laptop", BigDecimal.valueOf(999.99), null, item.getQuantity());
                    });

            CartItemResponse result = cartItemService.addCartItem(request);
            assertEquals(1, result.getQuantity());
        }
    }

    @Test
    public void testAddCartItemThrowsOnValidationsAndNotFound() {
        assertThrows(IllegalArgumentException.class, () -> cartItemService.addCartItem(new CartItemRequest(null, 1, 2)));
        assertThrows(IllegalArgumentException.class, () -> cartItemService.addCartItem(new CartItemRequest(1, 1, 0)));

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.empty());
        when(cartItemRepository.countByUser_Id(1)).thenReturn(50L);
        assertThrows(IllegalStateException.class, () -> cartItemService.addCartItem(new CartItemRequest(1, 1, 2)));

        when(cartItemRepository.countByUser_Id(1)).thenReturn(0L);
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> cartItemService.addCartItem(new CartItemRequest(1, 1, 2)));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> cartItemService.addCartItem(new CartItemRequest(1, 1, 2)));
    }

    @Test
    public void testUpdateCartItem() {
        CartItemRequest request = new CartItemRequest(1, 1, 7);

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        CartItemResponse updatedResponse = new CartItemResponse(1, 1, "Laptop", BigDecimal.valueOf(999.99), null, 7);

        try (MockedStatic<CartItemConverter> converter = Mockito.mockStatic(CartItemConverter.class)) {
            converter.when(() -> CartItemConverter.toCartItemResponse(cartItem)).thenReturn(updatedResponse);

            CartItemResponse result = cartItemService.updateCartItem(request);
            assertEquals(7, cartItem.getQuantity());
            assertEquals(7, result.getQuantity());
        }
    }

    @Test
    public void testUpdateCartItemThrowsOnValidationsAndNotFound() {
        assertThrows(IllegalArgumentException.class, () -> cartItemService.updateCartItem(new CartItemRequest(1, null, 2)));
        assertThrows(IllegalArgumentException.class, () -> cartItemService.updateCartItem(new CartItemRequest(1, 1, -3)));

        when(cartItemRepository.findByUser_IdAndProduct_ProductId(1, 1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> cartItemService.updateCartItem(new CartItemRequest(1, 1, 2)));
    }

    @Test
    public void testRemoveCartItemAndThrowsWhenNotFound() {
        when(cartItemRepository.existsById(1)).thenReturn(true);
        cartItemService.removeCartItem(1);
        verify(cartItemRepository, times(1)).deleteById(1);

        when(cartItemRepository.existsById(99)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> cartItemService.removeCartItem(99));
    }
}