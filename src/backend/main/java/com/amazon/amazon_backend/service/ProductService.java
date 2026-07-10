package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImagesUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.model.*;
import com.amazon.amazon_backend.repository.*;
import com.amazon.amazon_backend.utility.ProductConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazon.amazon_backend.utility.ProductConverter.toProductResponseList;

@Service
@Transactional
public class ProductService {

    private static final String DEFAULT_CATEGORY_NAME = "Other";
    private static final String DEFAULT_IMAGE_URL = "/photos/No-image-placeholder.png";

    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final TransactionRepository transactionRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CommentRepository commentRepository, TransactionRepository transactionRepository, RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.transactionRepository = transactionRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product ->{
                List<String> imageUrls = new ArrayList<>();
                for (Image img : product.getImages()) {
                    imageUrls.add(img.getImageUrl());
                }

                List<Comment> comments = commentRepository.findTop5ByProduct_ProductIdOrderByCommentIdDesc(product.getProductId());
                List<String> top5CommentsText = comments.stream()
                    .map(Comment::getCommentStr)
                    .toList();

               BigDecimal avgRating = product.getAverageRating();
               if (avgRating == null || avgRating.compareTo(BigDecimal.ZERO) == 0) {
                   Double calculatedAvg = ratingRepository.calculateAverageRatingByProduct(product.getProductId());
                   if (calculatedAvg != null) {
                       avgRating = BigDecimal.valueOf(calculatedAvg);
                   }
                   else {
                    avgRating = BigDecimal.ZERO;
                   }
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
                        avgRating,
                        top5CommentsText,
                        false
                );
        }).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Integer id, @RequestParam(required = false) Integer currentUserId){
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found."));

        List<Comment> comments = commentRepository.findTop5ByProduct_ProductIdOrderByCommentIdDesc(id);
        List<String> top5CommentsText = comments.stream()
                .map(Comment::getCommentStr)
                .toList();

        BigDecimal avgRating = product.getAverageRating();
        if (avgRating == null || avgRating.compareTo(BigDecimal.ZERO) == 0) {
            Double calculatedAvg = ratingRepository.calculateAverageRatingByProduct(id);
            if (calculatedAvg != null) {
                product.setAverageRating(BigDecimal.valueOf(calculatedAvg));
            }
        }

        boolean hasPurchased = false;
        if (currentUserId != null) {
            hasPurchased = transactionRepository.existsByBuyerIdAndItemsProductProductIdAndStatus(
                    currentUserId, id, TransactionStatus.SUCCESS
            );
        }

        return ProductConverter.toProductResponse(product, top5CommentsText, hasPurchased);
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

        return ProductConverter.toProductResponse(productRepository.save(product), List.of(), false);
    }

    public void deleteProduct(Integer id){
        if(!productRepository.existsById(id)){
            throw new NoSuchElementException("Product not found.");
        }
        productRepository.deleteById(id);
    }


    public ProductResponse updatePrice(Integer id, PriceUpdateRequest request){
        if(request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price is required and cannot be negative or zero");
        }
        Product product = findProduct(id);
        product.setPrice(request.getPrice());
        return ProductConverter.toProductResponse(productRepository.save(product), List.of(), false);
    }

    public ProductResponse updateQuantity(Integer id, QuantityUpdateRequest request) {
        if (request.getQuantity() == null || request.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity is required and cannot be negative.");
        }
        Product product = findProduct(id);
        product.setQuantity(request.getQuantity());
        return ProductConverter.toProductResponse(productRepository.save(product), List.of(), false);
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

        return ProductConverter.toProductResponse(productRepository.save(product), List.of(), false);
    }

    public ProductResponse updateNameAndDescription(Integer id, NameDescriptionUpdateRequest request) {
        Product product = findProduct(id);
        if (request.getProductName() != null && !request.getProductName().isBlank()) {
            product.setProductName(request.getProductName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        return ProductConverter.toProductResponse(productRepository.save(product), List.of(), false);
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
            return categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new NoSuchElementException("Category not found."));
        }
        return categoryRepository.findByCategoryName(DEFAULT_CATEGORY_NAME).orElseThrow(() -> new IllegalStateException("There is no category 'Other' in database"));
    }

    public void addProductReview(Integer id, Integer userId, String commentStr, Integer rating) {
        boolean hasPurchased = transactionRepository.existsByBuyerIdAndItemsProductProductIdAndStatus(userId, id, TransactionStatus.SUCCESS);

        if (!hasPurchased) {
            throw new IllegalStateException("Only users who purchased this product can leave a review.");
        }

        Product product = findProduct(id);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found."));

        if (commentStr != null && !commentStr.isBlank()) {
            Comment comment = new Comment(commentStr, product, user);
            commentRepository.save(comment);
        }
        if (rating != null) {
            Optional<Rating> existingRating = ratingRepository.findByUser_IdAndProduct_ProductId(userId, id);

            if (existingRating.isPresent()) {
                Rating ratingToUpdate = existingRating.get();
                ratingToUpdate.setStars(rating);
                ratingToUpdate.setCreatedAt(LocalDateTime.now());
                ratingRepository.save(ratingToUpdate);
            }
            else {
                Rating newRating = new Rating(user, product, rating, LocalDateTime.now());
                ratingRepository.save(newRating);
            }

            Double average = ratingRepository.calculateAverageRatingByProduct(id);
            if (average != null) {
                product.setAverageRating(BigDecimal.valueOf(average));
            } else {
                product.setAverageRating(BigDecimal.ZERO);
            }
        }
        productRepository.save(product);
    }

    public List<ProductResponse> getSimilarProducts(Integer categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId);
        return ProductConverter.toProductResponseList(products);
    }
}
