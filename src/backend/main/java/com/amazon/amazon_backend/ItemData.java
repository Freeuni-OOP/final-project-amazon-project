package com.amazon.amazon_backend;

import java.util.ArrayList;

public class ItemData {

    private ArrayList<Item> products;

    public void setProducts(ArrayList<Item> products){
        this.products = products;
    }

    public ArrayList<Item> getProducts(){
        return products;
    }

}
