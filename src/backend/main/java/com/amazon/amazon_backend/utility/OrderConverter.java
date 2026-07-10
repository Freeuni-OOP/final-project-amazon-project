package com.amazon.amazon_backend.utility;

import com.amazon.amazon_backend.dto.OrderDetailsResponse;
import com.amazon.amazon_backend.dto.OrderRequest;
import com.amazon.amazon_backend.dto.OrderResponse;
import com.amazon.amazon_backend.model.Order;
import com.amazon.amazon_backend.model.OrderDetails;
import com.amazon.amazon_backend.model.TransactionStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {
    private OrderConverter() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static OrderDetailsResponse toOrderDetailsResponse(OrderDetails details){
        OrderDetailsResponse response=new OrderDetailsResponse();


        response.setProductId(details.getProduct().getProductId());
        response.setPrice(details.getProduct().getPrice());
        response.setQuantity(details.getQuantity());
        response.setProductName(details.getProduct().getProductName());
        response.setAmount(details.getAmount());

        if (details.getProduct().getSeller() != null) {
            response.setSellerName(details.getProduct().getSeller().getUsername());
        }
        if (details.getProduct().getImages() != null && !details.getProduct().getImages().isEmpty()) {
            response.setImgUrl(details.getProduct().getImages().get(0).getImageUrl());
        } else {
            response.setImgUrl("/photos/No-image-placeholder.png");
        }
        if(details.getTransaction()!=null){
            response.setStatus(details.getTransaction().getStatus());
        }else{
            response.setStatus(TransactionStatus.PENDING);
        }

        return response;
    }

    public static OrderResponse toOrderResponse(Order order){
        OrderResponse response=new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getBuyer().getId());
        response.setUsername(order.getBuyer().getUsername());
        response.setDateTime(order.getDatetime());
        response.setTotalAmount(order.getTotalAmount());
        if(order.getOrderDetails()!=null){
            List<OrderDetailsResponse> items=order.getOrderDetails()
                    .stream().map(OrderConverter::toOrderDetailsResponse)
                    .collect(Collectors.toList());
            response.setItems(items);
        }

        return response;
    }
}
