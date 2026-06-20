package com.amazon.amazon_backend.service;

import com.amazon.amazon_backend.dto.CartItemRequest;
import com.amazon.amazon_backend.dto.CartItemResponse;
import com.amazon.amazon_backend.model.CartItem;
import com.amazon.amazon_backend.model.Product;
import com.amazon.amazon_backend.model.User;
import com.amazon.amazon_backend.repository.CartItemRepository;
import com.amazon.amazon_backend.repository.ProductRepository;
import com.amazon.amazon_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.amazon.amazon_backend.utility.CartItemConverter.toCartItemResponse;
import static com.amazon.amazon_backend.utility.CartItemConverter.toCartItemResponseList;

@Service
public class CartItemService {

    private static final int MAX_DISTINCT_ITEMS = 50;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public CartItemResponse getCartItemById(Integer id){
        return toCartItemResponse(cartItemRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Cart Item not found")));
    }

    public List<CartItemResponse> searchCartItemsByUserId(Integer userId){
        return toCartItemResponseList(cartItemRepository.findByUser_Id(userId));
    }

    public List<CartItemResponse> searchCartItemsByProductId(Integer productId){
        return toCartItemResponseList(cartItemRepository.findByProduct_ProductId(productId));
    }

    @Transactional
    public CartItemResponse updateCartItem(CartItemRequest request){
        if(request.getUserId()== null || request.getProductId() == null){
            throw new IllegalArgumentException("userID and productID are required");
        }
        if(request.getQuantity() == null){
            throw new IllegalArgumentException("Quantity is required.");
        }

        Optional<CartItem> existing = cartItemRepository.findByUser_IdAndProduct_ProductId(request.getUserId(), request.getProductId());

        if (request.getQuantity() <= 0) {
            existing.ifPresent(cartItemRepository::delete);
            return null;
        }

        if (existing.isPresent()) {
                CartItem cartItem = existing.get();
                cartItem.setQuantity(request.getQuantity());
                return toCartItemResponse(cartItemRepository.save(cartItem));
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found."));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found."));

        long distinctItemCount = cartItemRepository.countByUser_Id(request.getUserId());
        if(distinctItemCount >= MAX_DISTINCT_ITEMS){
            throw new IllegalStateException("There cannot be  more than " + MAX_DISTINCT_ITEMS + " distinct items in the cart");
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());

        return toCartItemResponse(cartItemRepository.save(cartItem));
    }

    @Transactional
    public void removeCartItem(Integer id) {
        if (!cartItemRepository.existsById(id)) {
            throw new NoSuchElementException("Cart Item not found");
        }
        cartItemRepository.deleteById(id);
    }

}
