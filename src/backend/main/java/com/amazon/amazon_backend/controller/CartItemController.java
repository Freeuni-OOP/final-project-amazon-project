package com.amazon.amazon_backend.controller;

import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.dto.CartItemRequest;
import com.amazon.amazon_backend.dto.ProductResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    public CartItemResponse getCartItemById(@PathVariable Integer id){
        return cartItemService.getCartItemById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CartItemResponse> getCartItemsByUserId(@PathVariable Integer userId) {
        return cartItemService.searchCartItemsByUserId(userId);

    }

    @GetMapping("/product/{productId}")
    public List<CartItemResponse> getCartItemsByProductId(@PathVariable Integer productId) {
        return cartItemService.searchCartItemsByProductId(productId);

    }

    @PostMapping("/add")
    public CartItemResponse addCartItem(@RequestBody CartItemRequest request) {
        return cartItemService.addCartItem(request);
    }
    
    @PostMapping("/update")
    public CartItemResponse updateCartItem(@RequestBody CartItemRequest request) {
        return cartItemService.updateCartItem(request);
    }

    @DeleteMapping("/{id}")
        public String removeCartItem(@PathVariable Integer id){
            cartItemService.removeCartItem(id);
            return "Item successfully removed from the cart.";
        }
}
