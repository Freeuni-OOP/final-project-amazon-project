package com.amazon.amazon_backend.model;
import com.amazon.amazon_backend.repository.ProductRepository;

import java.util.ArrayList;

public class Search {

    // Object to reach data of all products
    private ProductRepository productRepository;

    public Search(){
        productRepository = new ProductRepository();
    }

    public ArrayList<Product> getProductsWithName(String productName){
        return null;
    }

}
