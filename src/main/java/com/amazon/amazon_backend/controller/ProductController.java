package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.ProductRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.ImageUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.NameDescriptionUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.PriceUpdateRequest;
import com.amazon.amazon_backend.dto.ProductUpdateRequests.QuantityUpdateRequest;
import com.amazon.amazon_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")

public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
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

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
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

    @PatchMapping("/{id}/image")
    public ProductResponse updateImage(@PathVariable Integer id, @RequestBody ImageUpdateRequest request) {
        return productService.updateImage(id, request);
    }

    @PatchMapping("/{id}/details")
    public ProductResponse updateNameAndDescription(@PathVariable Integer id, @RequestBody NameDescriptionUpdateRequest request) {
        return productService.updateNameAndDescription(id, request);
    }

}
