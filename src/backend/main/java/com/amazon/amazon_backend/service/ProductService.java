package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImagesUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.model.Category;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CartItemRepository;
import com.amazon.amazon_backend.repository.CategoryRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazon.amazon_backend.model.Image;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.amazon.amazon_backend.utility.ProductConverter.toProductResponse;
import static com.amazon.amazon_backend.utility.ProductConverter.toProductResponseList;

@Service
@Transactional
public class ProductService {

    private static final String DEFAULT_CATEGORY_NAME = "Other";
    private static final String DEFAULT_IMAGE_URL = "/photos/No-image-placeholder.png";

    private final ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product ->{
                List<String> imageUrls = new ArrayList<>();
                for (Image img : product.getImages()) {
                    imageUrls.add(img.getImageUrl());
                }
                 return new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        imageUrls,
                        product.getCategory() != null ? product.getCategory().getCategoryName() : "No Category",
                        product.getSeller() != null ? product.getSeller().getUsername() : "Unknown Seller",
                         product.getAverageRating()
                );
        }).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Integer id){
        return toProductResponse(productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Product not found.")));
    }

    public List<ProductResponse> searchProductsByName(String name) {
        return toProductResponseList(productRepository.findByProductNameContainingIgnoreCase(name));
    }

    public List<ProductResponse> searchProductsByCategoryId(Integer categoryId) {
        return toProductResponseList(productRepository.findByCategory_CategoryId(categoryId));
    }

    public List<ProductResponse> searchProductsByCategoryName(String categoryName){
        return toProductResponseList(productRepository.findByCategory_CategoryNameIgnoreCase(categoryName));
    }

    public List<ProductResponse> searchProductsBySellerId(Integer sellerId){
        return toProductResponseList(productRepository.findBySeller_Id(sellerId));
    }

    public ProductResponse createProduct(ProductRequest request){
        if(request.getProductName() == null || request.getProductName().isBlank()){
            throw new IllegalArgumentException("Product name is required.");
        }
        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price is required and cannot be negative or zero.");
        }
        if(request.getSellerId() == null){
            throw new IllegalArgumentException("Seller id is required.");
        }

        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(()-> new NoSuchElementException("Seller not found."));

        Category category = resolveCategory(request.getCategoryId(), request.getCategoryName());

        Integer quantity = request.getQuantity();
        if(quantity == null){
            quantity = 1;

        }else if(quantity < 0){
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }

        Product product = Product.builder()
                .productName(request.getProductName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(quantity)
                .seller(seller)
                .category(category)
                .images(new ArrayList<>())
                .averageRating(BigDecimal.ZERO)
                .build();

        List<String> requestedImages = request.getImageUrls();
        if (requestedImages == null || requestedImages.isEmpty()) {
            product.getImages().add(new Image(null, product, DEFAULT_IMAGE_URL));
        }
        else {
            if (requestedImages.size() > 5) {
                throw new IllegalArgumentException("You can upload up to 5 images for a product");
            }
            for (String imageUrl : requestedImages) {
                if (imageUrl != null && !imageUrl.isBlank()) {
                    product.getImages().add(new Image(null, product, imageUrl));
                }
            }
        }

        return toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(Integer id){
        if(!productRepository.existsById(id)){
            throw new NoSuchElementException("Product not found.");
        }
        cartItemRepository.deleteByProduct_ProductId(id);
        productRepository.deleteById(id);
    }


    public ProductResponse updatePrice(Integer id, PriceUpdateRequest request){
        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price is required and cannot be negative or zero");
        }
        Product product = findProduct(id);
        product.setPrice(request.getPrice());
        return toProductResponse(productRepository.save(product));
    }

    public ProductResponse updateQuantity(Integer id, QuantityUpdateRequest request) {
        if (request.getQuantity() == null || request.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity is required and cannot be negative.");
        }
        Product product = findProduct(id);
        product.setQuantity(request.getQuantity());
        return toProductResponse(productRepository.save(product));
    }

    public ProductResponse updateImage(Integer id, ImagesUpdateRequest request) {
        Product product = findProduct(id);

        product.getImages().clear();

        List<String> requestedImages = request.getImageUrls();
        if (requestedImages == null || requestedImages.isEmpty()) {
            product.getImages().add(new Image(null, product, DEFAULT_IMAGE_URL));
        }
        else {
            if (requestedImages.size() > 5) {
                throw new IllegalArgumentException("You can upload up to 5 images for a product");
            }
            for (String imageUrl : requestedImages) {
                if (imageUrl != null && !imageUrl.isBlank()) {
                    product.getImages().add(new Image(null, product, imageUrl));
                }
            }
        }

        return toProductResponse(productRepository.save(product));
    }

    public ProductResponse updateNameAndDescription(Integer id, NameDescriptionUpdateRequest request) {
        Product product = findProduct(id);
        if (request.getProductName() != null && !request.getProductName().isBlank()) {
            product.setProductName(request.getProductName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        return toProductResponse(productRepository.save(product));
    }

    private Product findProduct(Integer id){
        return productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Product not found."));
    }
    private Category resolveCategory(Integer categoryId, String categoryName) {
        if (categoryId != null) {
            return categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NoSuchElementException("Category not found."));
        }
        if (categoryName != null && !categoryName.isBlank()) {
            return categoryRepository.findByCategoryName(categoryName)
                    .orElseThrow(() -> new NoSuchElementException("Category not found."));
        }
        return categoryRepository.findByCategoryName(DEFAULT_CATEGORY_NAME)
                .orElseThrow(() -> new IllegalStateException("There is no category 'Other' in database"));
    }

}
