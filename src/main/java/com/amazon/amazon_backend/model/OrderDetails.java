package com.amazon.amazon_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="Order_Details")
@Setter
@Getter
@NoArgsConstructor
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Order_Details_ID")
    private Integer orderDetailID;

    @ManyToOne
    @JoinColumn(name="Product_ID", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="Order_ID", nullable = false)
    private Order order;

    @Column(name="Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    public OrderDetails(Integer orderDetailID, Product product,
                        Order order, Integer quantity, BigDecimal amount){
        this.orderDetailID=orderDetailID;
        this.product=product;
        this.order=order;
        this.quantity=quantity;
        this.amount=amount;
    }
}
