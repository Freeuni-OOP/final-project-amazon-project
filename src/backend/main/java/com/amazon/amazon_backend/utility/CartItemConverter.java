package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartItemConverter {
    public static CartItemResponse toCartItemResponse(CartItem cartItem){
        if(cartItem == null)return null;

        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getProductId(),
                cartItem.getProduct().getProductName(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getImgUrl(),
                cartItem.getQuantity()
        );
    }


    public static List<CartItemResponse> toCartItemResponseList(List<CartItem> cartItems) {
        List<CartItemResponse> responseList = new ArrayList<>();
        if (cartItems == null) return responseList;

        for (CartItem cartItem : cartItems) {
            responseList.add(toCartItemResponse(cartItem));
        }

        return responseList;
    }
}
