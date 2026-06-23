package com.amazon.amazon_backend;

import com.amazon.amazon_backend.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Item {

    private int productId;
    private String productName;
    private String description;
    private String[] images;
    private int quantity;
    private String category;
    private BigDecimal price;

    public int getProductId(){
        return this.productId;
    }

    public void setProductId(int productId){
        this.productId = productId;
    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String[] getImages(){
        return this.images;
    }

    public void setImages(String[] images){
        this.images = images;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public BigDecimal getPrice(){
        return this.price;
    }

    public void setPrice(BigDecimal price){
        this.price= price;
    }

}
