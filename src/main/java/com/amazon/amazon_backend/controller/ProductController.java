package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.ProductResponse;
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
        return productService.SearchProductsByName(name);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategoryId(@PathVariable Integer categoryId) {
        return productService.SearchProductsByCategoryId(categoryId);

    }

    @GetMapping("/category-name/{categoryName}")
    public List<ProductResponse> getProductsByCategoryName(@PathVariable String categoryName) {
        return productService.SearchProductsByCategoryName(categoryName);
    }

    @GetMapping("/seller/{sellerId}")
    public List<ProductResponse> getProductsBySellerId(@PathVariable Integer sellerId) {
        return productService.SearchProductsBySellerId(sellerId);
    }


}
