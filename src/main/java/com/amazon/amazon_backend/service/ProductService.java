package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.utility.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.amazon.amazon_backend.utility.ProductConverter.toProductResponse;
import static com.amazon.amazon_backend.utility.ProductConverter.toProductResponseList;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse getProductById(Integer id){
        return toProductResponse(productRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Product not found.")));
    }

    public List<ProductResponse> SearchProductsByName(String name) {
        return toProductResponseList(productRepository.findByProductNameContainingIgnoreCase(name));
    }

    public List<ProductResponse> SearchProductsByCategoryId(Integer categoryId) {
        return toProductResponseList(productRepository.findByCategory_CategoryId(categoryId));
    }

    public List<ProductResponse> SearchProductsByCategoryName(String categoryName){
        return toProductResponseList(productRepository.findByCategory_CategoryNameIgnoreCase(categoryName));
    }

    public List<ProductResponse> SearchProductsBySellerId(Integer sellerId){
        return toProductResponseList(productRepository.findBySeller_Id(sellerId));
    }


}
