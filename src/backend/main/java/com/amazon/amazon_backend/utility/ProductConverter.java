package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.model.Image;
import com.amazon.amazon_backend.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductConverter {

    public static ProductResponse toProductResponse(Product product){
        if(product == null)return null;

        List<String> imageUrls = new ArrayList<>();
        if (product.getImages() != null) {
            for (Image img : product.getImages()) {
                imageUrls.add(img.getImageUrl());
            }
        }

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                imageUrls,
                product.getCategory().getCategoryName(),
                product.getSeller().getUsername(),
                product.getAverageRating()
                );
    }

    public static List<ProductResponse> toProductResponseList(List<Product> products) {
        List<ProductResponse> responseList = new ArrayList<>();
        if (products == null) return responseList;

        for (Product product : products) {
            responseList.add(toProductResponse(product));
        }

        return responseList;
    }
}
