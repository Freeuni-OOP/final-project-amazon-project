package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImagesUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.*;
import com.amazon.amazon_backend.utility.ProductConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ProductService productService;

    private User seller;
    private Category category;
    private Product product;
    private ProductResponse response;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(productService, "userRepository", userRepository);
        ReflectionTestUtils.setField(productService, "categoryRepository", categoryRepository);

        seller = new User();
        category = new Category("Electronics");
        product = new Product("Description", seller, category, "Laptop", BigDecimal.valueOf(999.99), 10, new ArrayList<>());
        response = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        when(commentRepository.findTop5ByProduct_ProductIdOrderByCommentIdDesc(any()))
                .thenReturn(List.of());

        when(transactionRepository.existsByBuyerIdAndItemsProductProductIdAndStatus(any(), any(), any()))
                .thenReturn(false);

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(), anyList(), anyList(), anyBoolean()))
                    .thenReturn(response);

            ProductResponse result = productService.getProductById(1, 1);

            assertNotNull(result);
            assertEquals("Laptop", result.getProductName());
        }
    }

    @Test
    public void testGetProductByIdThrowsWhenNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProductById(99, 1));
    }

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> results = productService.getAllProducts();
        assertEquals(1, results.size());
        assertEquals("Laptop", results.get(0).getProductName());
    }

    @Test
    public void testSearchProductsByName() {
        when(productRepository.findByProductNameContainingIgnoreCase("laptop")).thenReturn(List.of(product));

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponseList(any())).thenReturn(List.of(response));

            List<ProductResponse> results = productService.searchProductsByName("laptop");
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testSearchProductsByCategoryId() {
        when(productRepository.findByCategory_CategoryId(1)).thenReturn(List.of(product));

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponseList(any())).thenReturn(List.of(response));

            List<ProductResponse> results = productService.searchProductsByCategoryId(1);
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testSearchProductsByCategoryName() {
        when(productRepository.findByCategory_CategoryNameIgnoreCase("electronics")).thenReturn(List.of(product));

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponseList(any())).thenReturn(List.of(response));

            List<ProductResponse> results = productService.searchProductsByCategoryName("electronics");
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testSearchProductsBySellerId() {
        when(productRepository.findBySeller_Id(1)).thenReturn(List.of(product));

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponseList(any())).thenReturn(List.of(response));

            List<ProductResponse> results = productService.searchProductsBySellerId(1);
            assertEquals(1, results.size());
            assertEquals("Laptop", results.get(0).getProductName());
        }
    }

    @Test
    public void testCreateProduct_WithCategoryIdAndImages() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, 1, null, List.of("/photos/laptop.png"));

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(imageRepository.saveAll(any())).thenReturn(List.of());

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(), anyList(),anyBoolean()))
                    .thenReturn(response);

            ProductResponse result = productService.createProduct(request);
            assertNotNull(result);
            assertEquals("Laptop", result.getProductName());
        }
    }

    @Test
    public void testCreateProduct_WithCategoryNameInsteadOfId() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, null, "Electronics", List.of("/photos/laptop.png"));

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(imageRepository.saveAll(any())).thenReturn(List.of());

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(), anyList(), anyBoolean()))
                    .thenReturn(response);

            ProductResponse result = productService.createProduct(request);
            assertNotNull(result);
            assertEquals("Laptop", result.getProductName());
        }
    }

    @Test
    public void testCreateProduct_DefaultsToOtherCategoryWhenNoneProvided() {
        Category otherCategory = new Category("Other");
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, null, null, List.of("/photos/laptop.png"));

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findByCategoryName("Other")).thenReturn(Optional.of(otherCategory));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(imageRepository.saveAll(any())).thenReturn(List.of()); // ⚡

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(), anyList(),anyBoolean()))
                    .thenReturn(response);

            ProductResponse result = productService.createProduct(request);
            assertNotNull(result);
        }
    }

    @Test
    public void testCreateProduct_DefaultsQuantityToOneWhenNull() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), null, 1, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(imageRepository.saveAll(any())).thenReturn(List.of()); // ⚡

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(),anyList(), anyBoolean()))
                    .thenAnswer(invocation -> {
                        Product savedProduct = invocation.getArgument(0);
                        return new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), savedProduct.getQuantity(), List.of("/photos/No-image-placeholder.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);
                    });

            ProductResponse result = productService.createProduct(request);
            assertEquals(1, result.getQuantity());
        }
    }

    @Test
    public void testCreateProduct_UsesDefaultImageWhenNoneProvided() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, 1, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(imageRepository.saveAll(any())).thenReturn(List.of()); // ⚡

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(),anyList(), anyBoolean()))
                    .thenAnswer(invocation -> {
                        Product savedProduct = invocation.getArgument(0);
                        List<String> urls = new ArrayList<>();
                        if (savedProduct.getImages() != null) {
                            for (Image img : savedProduct.getImages()) {
                                urls.add(img.getImageUrl());
                            }
                        }
                        return new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, urls, "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);
                    });

            ProductResponse result = productService.createProduct(request);
            assertEquals(1, result.getImageUrls().size());
            assertEquals("/photos/No-image-placeholder.png", result.getImageUrls().get(0));
        }
    }

    @Test
    public void testCreateProduct_UsesProvidedImages() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, 1, null, List.of("/photos/a.png", "/photos/b.png"));

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(imageRepository.saveAll(any())).thenReturn(List.of()); // ⚡

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(any(Product.class), anyList(),anyList(), anyBoolean()))
                    .thenAnswer(invocation -> {
                        Product savedProduct = invocation.getArgument(0);
                        List<String> urls = new ArrayList<>();
                        if (savedProduct.getImages() != null) {
                            for (Image img : savedProduct.getImages()) {
                                urls.add(img.getImageUrl());
                            }
                        }
                        return new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, urls, "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);
                    });

            ProductResponse result = productService.createProduct(request);
            assertEquals(2, result.getImageUrls().size());
        }
    }

    @Test
    public void testCreateProductThrowsWhenNameMissing() {
        ProductRequest request = new ProductRequest(1, null, "Description", BigDecimal.valueOf(10), 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenNameBlank() {
        ProductRequest request = new ProductRequest(1, "   ", "Description", BigDecimal.valueOf(10), 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenPriceMissing() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", null, 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenPriceZeroOrNegative() {
        ProductRequest zeroPriceRequest = new ProductRequest(1, "Laptop", "Description", BigDecimal.ZERO, 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(zeroPriceRequest));

        ProductRequest negativePriceRequest = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(-5), 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(negativePriceRequest));
    }

    @Test
    public void testCreateProductThrowsWhenSellerIdMissing() {
        ProductRequest request = new ProductRequest(null, "Laptop", "Description", BigDecimal.valueOf(10), 10, 1, null, null);
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenSellerNotFound() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(10), 10, 1, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenCategoryIdNotFound() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(10), 10, 1, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenCategoryNameNotFound() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(10), 10, null, "Nonexistent", null);

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findByCategoryName("Nonexistent")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenQuantityNegative() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(10), -1, 1, null, null);

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testCreateProductThrowsWhenTooManyImages() {
        ProductRequest request = new ProductRequest(1, "Laptop", "Description", BigDecimal.valueOf(10), 10, 1, null,
                List.of("1.png", "2.png", "3.png", "4.png", "5.png", "6.png"));

        when(userRepository.findById(1)).thenReturn(Optional.of(seller));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(request));
    }

    @Test
    public void testDeleteProductAndThrowsWhenNotFound() {
        when(productRepository.existsById(1)).thenReturn(true);
        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);

        when(productRepository.existsById(99)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> productService.deleteProduct(99));
    }

    @Test
    public void testUpdatePrice() {
        PriceUpdateRequest request = new PriceUpdateRequest(BigDecimal.valueOf(1199.99));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponse updatedResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(1199.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(product, List.of(), List.of(), false)).thenReturn(updatedResponse);

            ProductResponse result = productService.updatePrice(1, request);
            assertEquals(BigDecimal.valueOf(1199.99), product.getPrice());
            assertEquals(BigDecimal.valueOf(1199.99), result.getPrice());
        }
    }

    @Test
    public void testUpdatePriceThrowsOnValidationsAndNotFound() {
        assertThrows(IllegalArgumentException.class, () -> productService.updatePrice(1, new PriceUpdateRequest(null)));
        assertThrows(IllegalArgumentException.class, () -> productService.updatePrice(1, new PriceUpdateRequest(BigDecimal.ZERO)));

        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> productService.updatePrice(1, new PriceUpdateRequest(BigDecimal.valueOf(10))));
    }

    @Test
    public void testUpdateQuantity() {
        QuantityUpdateRequest request = new QuantityUpdateRequest(25);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponse updatedResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 25, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(product, List.of(), List.of(), false)).thenReturn(updatedResponse);

            ProductResponse result = productService.updateQuantity(1, request);
            assertEquals(25, product.getQuantity());
            assertEquals(25, result.getQuantity());
        }
    }

    @Test
    public void testUpdateQuantityThrowsOnValidationsAndNotFound() {
        assertThrows(IllegalArgumentException.class, () -> productService.updateQuantity(1, new QuantityUpdateRequest(null)));
        assertThrows(IllegalArgumentException.class, () -> productService.updateQuantity(1, new QuantityUpdateRequest(-5)));

        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> productService.updateQuantity(1, new QuantityUpdateRequest(5)));
    }

    @Test
    public void testUpdateImage() {
        ImagesUpdateRequest request = new ImagesUpdateRequest(List.of("/photos/new-laptop.png"));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponse updatedResponse = new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, List.of("/photos/new-laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(),List.of(), false);

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(product, List.of(), List.of(), false)).thenReturn(updatedResponse);

            ProductResponse result = productService.updateImage(1, request);
            assertEquals(1, result.getImageUrls().size());
            assertEquals("/photos/new-laptop.png", result.getImageUrls().get(0));
        }
    }

    @Test
    public void testUpdateImageUsesDefaultWhenListEmpty() {
        ImagesUpdateRequest request = new ImagesUpdateRequest(new ArrayList<>());

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenAnswer(invocation -> invocation.getArgument(0));

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(product, List.of(), List.of(),false))
                    .thenAnswer(invocation -> {
                        List<String> urls = new ArrayList<>();
                        for (Image img : product.getImages()) {
                            urls.add(img.getImageUrl());
                        }
                        return new ProductResponse(1, "Laptop", "Description", BigDecimal.valueOf(999.99), 10, urls, "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);
                    });

            ProductResponse result = productService.updateImage(1, request);
            assertEquals(1, result.getImageUrls().size());
            assertEquals("/photos/No-image-placeholder.png", result.getImageUrls().get(0));
        }
    }

    @Test
    public void testUpdateImageThrowsWhenTooManyImages() {
        ImagesUpdateRequest request = new ImagesUpdateRequest(List.of("1.png", "2.png", "3.png", "4.png", "5.png", "6.png"));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> productService.updateImage(1, request));
    }

    @Test
    public void testUpdateImageThrowsWhenProductNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.updateImage(99, new ImagesUpdateRequest(List.of("/photos/laptop.png"))));
    }

    @Test
    public void testUpdateNameAndDescription() {
        NameDescriptionUpdateRequest request = new NameDescriptionUpdateRequest("Gaming Laptop", "Updated description");

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponse updatedResponse = new ProductResponse(1, "Gaming Laptop", "Updated description", BigDecimal.valueOf(999.99), 10, List.of("/photos/laptop.png"), "Electronics", "sellerUser", BigDecimal.ZERO, List.of(), List.of(), false);

        try (MockedStatic<ProductConverter> converter = Mockito.mockStatic(ProductConverter.class)) {
            converter.when(() -> ProductConverter.toProductResponse(product, List.of(), List.of(),false)).thenReturn(updatedResponse);

            ProductResponse result = productService.updateNameAndDescription(1, request);
            assertEquals("Gaming Laptop", product.getProductName());
            assertEquals("Updated description", product.getDescription());
            assertEquals("Gaming Laptop", result.getProductName());
        }
    }


    @Test
    public void testUpdateNameAndDescriptionThrowsWhenNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.updateNameAndDescription(99, new NameDescriptionUpdateRequest("Name", "Desc")));
    }
}