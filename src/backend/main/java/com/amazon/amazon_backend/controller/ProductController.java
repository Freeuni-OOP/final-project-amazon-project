package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImagesUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.dto.ReviewRequest;
import com.amazon.amazon_backend.repository.CommentRepository;
import com.amazon.amazon_backend.repository.RatingRepository;
import com.amazon.amazon_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id, @RequestParam(required = false) Integer userId) {
        return productService.getProductById(id, userId);
    }

    @GetMapping("/search")
    public List<ProductResponse> searchProductsByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return productService.searchProductsByCategoryId(categoryId);
    }

    @GetMapping("/category-name/{categoryName}")
    public List<ProductResponse> getProductsByCategoryName(@PathVariable String categoryName) {
        return productService.searchProductsByCategoryName(categoryName);
    }

    @GetMapping("/seller/{sellerId}")
    public List<ProductResponse> getProductsBySellerId(@PathVariable Integer sellerId) {
        return productService.searchProductsBySellerId(sellerId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse createProduct(@RequestPart("product") ProductRequest request, @RequestPart(value = "images", required = false) MultipartFile[] imageFiles) throws IOException {

        List<String> fileNames = new ArrayList<>();
        if(imageFiles != null && imageFiles.length > 0 && !imageFiles[0].isEmpty()){
            String uploadDir = System.getProperty("user.dir") + "/src/backend/main/resources/static/photos/";

            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    String uniqueID = UUID.randomUUID().toString();
                    String uniqueFileName = uniqueID + "_" + file.getOriginalFilename();
                    file.transferTo(new File(uploadDir + uniqueFileName));
                    fileNames.add("/photos/" + uniqueFileName);
                }
            }
        }
        else {
            fileNames.add("/photos/No-image-placeholder.png");
        }

        if (!fileNames.isEmpty()) {
            request.setImageUrls(fileNames);
        }

        return productService.createProduct(request);
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<String> addProductReview(@PathVariable Integer id, @RequestBody ReviewRequest request, @RequestParam Integer userId) {
        try {
            productService.addProductReview(id, userId, request.getComment_STR(), request.getRating());
            return ResponseEntity.ok("Review added successfully!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return "Product deleted successfully.";
    }

    @PatchMapping("/{id}/price")
    public ProductResponse updatePrice(@PathVariable Integer id, @RequestBody PriceUpdateRequest request) {
        return productService.updatePrice(id, request);
    }

    @PatchMapping("/{id}/quantity")
    public ProductResponse updateQuantity(@PathVariable Integer id, @RequestBody QuantityUpdateRequest request) {
        return productService.updateQuantity(id, request);
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse updateImage(@PathVariable Integer id, @RequestPart("images") MultipartFile[] imageFiles) throws IOException {
        List<String> fileNames = new ArrayList<>();
        String uploadDir = System.getProperty("user.dir") + "/src/backend/main/resources/static/photos/";

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                String uniqueID = UUID.randomUUID().toString();
                String uniqueFileName = uniqueID + "_" + file.getOriginalFilename();

                Path targetPath = Paths.get(uploadDir).resolve(uniqueFileName);
                Files.createDirectories(targetPath.getParent());
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                fileNames.add("/photos/" + uniqueFileName);
            }
        }

        ImagesUpdateRequest request = new ImagesUpdateRequest();
        request.setImageUrls(fileNames);

        return productService.updateImage(id, request);
    }

    @PatchMapping("/{id}/details")
    public ProductResponse updateNameAndDescription(@PathVariable Integer id, @RequestBody NameDescriptionUpdateRequest request) {
        return productService.updateNameAndDescription(id, request);
    }
}
